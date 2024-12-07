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

        val (header, payloadEncode, signature) = token.split('.')
        return parser.parsePayload(Base64.decode(payloadEncode).decodeToString())
    }

    fun extractLogin(payload: Payload) = payload.getClaim("login")?.asString()
}