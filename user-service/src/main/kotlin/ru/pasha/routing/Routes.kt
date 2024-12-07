package ru.pasha.routing

import io.ktor.http.*
import io.ktor.server.auth.*
import io.ktor.server.resources.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import ru.pasha.domain.commands.Commands
import ru.pasha.domain.services.UserService
import ru.pasha.plugins.JwtAuthKey


fun Route.configureUserRoutes(userService: UserService) {
    getUsersRoute(userService)
}

private fun Route.getUsersRoute(userService: UserService) {
    authenticate(JwtAuthKey) {
        get<Users> {
            val command = Commands.GetUsers
            userService.getUsers(command)
                .onFailure { e -> call.respond(HttpStatusCode.InternalServerError, e.message.toString()) }
                .onSuccess { users -> call.respond(users) }
        }
    }
}
