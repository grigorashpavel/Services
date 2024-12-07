package ru.pasha.plugins

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.response.*
import org.koin.ktor.ext.inject
import ru.pasha.domain.commands.Commands
import ru.pasha.domain.services.UserService
import ru.pasha.util.JwtExtractor
import java.time.Instant


const val JwtAuthKey = "auth-jwt"

fun Application.configureJwtAuth() {
    val userService by inject<UserService>()

    install(Authentication) {
        jwt(JwtAuthKey) {
            verifier {
                JWT.require(Algorithm.HMAC256(Config.JWT_SECRET))
                    .withIssuer(Config.JWT_ISS)
                    .build()
            }

            validate { credential ->
                val payload = credential.payload

                val expiresAt = payload.expiresAt?.toInstant()
                val currentInstant = Instant.now()

                val isExpired = expiresAt?.isBefore(currentInstant) == true
                if (isExpired) {
                    return@validate null
                }

                val login = JwtExtractor.extractLogin(payload)
                if (login == null) {
                    return@validate null
                }

                val isUserNotExist = userService.getUserByLogin(Commands.GetUserByLogin(login))
                    .onFailure { e ->  }
                    .getOrNull() == null

                if (isUserNotExist) {
                    return@validate null
                }

                JWTPrincipal(payload)
            }

            challenge { _, _ ->
                call.respond(HttpStatusCode.Unauthorized)
            }
        }
    }

}