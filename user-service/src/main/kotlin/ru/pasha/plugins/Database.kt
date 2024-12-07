package ru.pasha.plugins

import io.ktor.server.application.*
import ru.pasha.database.DatabaseService


fun Application.configureDatabase() {
    val database = DatabaseService()
}