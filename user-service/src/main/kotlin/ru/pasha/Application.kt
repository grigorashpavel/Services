package ru.pasha

import io.ktor.server.application.*
import io.ktor.server.netty.*
import ru.pasha.plugins.*


fun main(args: Array<String>) = EngineMain.main(args)

fun Application.module() {
    configureSerialization()

    configureHTTP()
    configureSockets()

    configureRouting()

    configureSwagger()

    configureMonitoring()
    configureLogs()
}
