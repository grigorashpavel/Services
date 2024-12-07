package ru.pasha.routing

import io.ktor.http.*
import io.ktor.server.resources.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import ru.pasha.data.mappers.toDto
import ru.pasha.data.repositories.UserRepositoryImpl
import ru.pasha.database.DatabaseService
import ru.pasha.domain.commands.Commands
import ru.pasha.domain.services.UserService
import ru.pasha.routing.dto.UserDto


fun Route.configureUserRoutes() {
    getUsersRoute()
}

val fakeRepo = UserRepositoryImpl(DatabaseService())

private val userService = object : UserService {
    override suspend fun getUsers(command: Commands.GetUsers): Result<List<UserDto>> = runCatching {
        fakeRepo.getUsers().map { it.toDto() }
    }
}

private fun Route.getUsersRoute() {
    get<Users> {
        val command = Commands.GetUsers
        userService.getUsers(command)
            .onFailure { e -> call.respond(HttpStatusCode.InternalServerError, e.message.toString()) }
            .onSuccess { users -> call.respond(users) }
    }
}
