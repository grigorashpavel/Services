package ru.pasha

import Config
import io.ktor.server.application.*
import io.ktor.server.netty.*

fun main(args: Array<String>) = EngineMain.main(args)

fun Application.module() {
    configureRouting()
    configureHTTP()
    configureSockets()
    configureSecurity()
    configureDatabases()
    configureFrameworks()
    configureMonitoring()
    configureSerialization()
}