package ru.pasha.plugins

import io.ktor.server.application.*
import io.ktor.server.plugins.*
import io.ktor.server.plugins.calllogging.*
import io.ktor.server.request.*
import org.slf4j.event.Level


fun Application.configureMonitoring() {
    install(CallLogging) {
        level = Level.INFO

        filter { call -> call.request.path().startsWith("/api/v1/") }

        format { call ->
            val httpMethod = call.request.httpMethod.value
            val uri = call.request.uri
            val status = call.response.status()?.value
            val userAgent = call.request.userAgent() ?: "Unknown"
            val clientIP = call.request.origin.remoteHost

            "Method: $httpMethod, URI: $uri, Status: $status, User-Agent: $userAgent, Client-IP: $clientIP"
        }
    }
}
