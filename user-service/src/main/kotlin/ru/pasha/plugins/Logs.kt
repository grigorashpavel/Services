package ru.pasha.plugins

import io.ktor.server.application.*
import org.koin.ktor.plugin.Koin
import org.koin.logger.slf4jLogger


fun Application.configureLogs() {
    install(Koin) {
        slf4jLogger()
    }
}
