package ru.pasha.plugins

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.resources.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject
import ru.pasha.domain.services.UserService
import ru.pasha.routing.ApiV1
import ru.pasha.routing.configureApiV1BaseRoute
import ru.pasha.routing.configureUserRoutes


fun Application.configureRouting() {

    install(StatusPages) {
        exception<Throwable> { call, cause ->
            call.respondText(text = cause.message.orEmpty() , status = HttpStatusCode.InternalServerError)
        }
    }
    install(Resources)
    install(IgnoreTrailingSlash)

    val userService by inject<UserService>()
    routing {
        configureApiV1BaseRoute()
        configureUserRoutes(userService)
    }
}
