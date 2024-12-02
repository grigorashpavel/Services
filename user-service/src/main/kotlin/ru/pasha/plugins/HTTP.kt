package ru.pasha.plugins

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.cors.routing.*
import io.ktor.server.plugins.partialcontent.*


fun Application.configureHTTP() {
    install(PartialContent) {
            maxRangeCount = 10
        }
    install(CORS) {
        anyHost()
        allowHeader(HttpHeaders.ContentType)
    }
}
