package ru.pasha.plugins

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.resources.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject
import ru.pasha.domain.services.UserService
import ru.pasha.routing.Paths
import ru.pasha.routing.configureUserRoutes


fun Application.configureRouting() {
    install(StatusPages) {
        exception<Throwable> { call, cause ->
            call.respondText(text = "500: $cause" , status = HttpStatusCode.InternalServerError)
        }
    }
    install(Resources)

    val userService by inject<UserService>()
    routing {
        route(Paths.Base) {
            route("/") {
                get {
                    call.respond(HttpStatusCode.OK, hashMapOf("status" to "ok"))
                }

                configureUserRoutes(userService)
            }
        }
    }
}
