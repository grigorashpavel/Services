package ru.pasha.util

import com.auth0.jwt.impl.JWTParser
import com.auth0.jwt.interfaces.Payload
import io.ktor.http.*
import io.ktor.server.request.*
import kotlin.io.encoding.Base64
import kotlin.io.encoding.ExperimentalEncodingApi

object JwtExtractor {
    private val parser = JWTParser()

    fun extractToken(request: ApplicationRequest): String? =
        request.headers[HttpHeaders.Authorization]?.split(' ')?.get(1)


    @OptIn(ExperimentalEncodingApi::class)
    fun extractPayload(token: String?): Payload? {
        if (token == null) return null

        try {
            val (_, payloadEncode, _) = token.split('.')
            return parser.parsePayload(
                Base64.UrlSafe.withPadding(Base64.PaddingOption.ABSENT_OPTIONAL)
                    .decode(payloadEncode).decodeToString()
            )
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }
    }

    fun extractLogin(payload: Payload) = payload.getClaim("login")?.asString()

    fun extractLoginFromRequest(request: ApplicationRequest): String? {
        val token = extractToken(request)
        val payload = extractPayload(token)

        return payload?.let(::extractLogin)
    }

}