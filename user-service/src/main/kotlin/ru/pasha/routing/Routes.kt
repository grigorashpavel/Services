package ru.pasha.routing

import io.ktor.http.*
import io.ktor.server.auth.*
import io.ktor.server.resources.get
import io.ktor.server.resources.post
import io.ktor.server.response.respond
import io.ktor.server.routing.*
import ru.pasha.domain.commands.Commands
import ru.pasha.domain.services.UserService
import ru.pasha.plugins.JwtAuthExistUserKey
import ru.pasha.plugins.JwtAuthNotExistUserKey
import ru.pasha.util.JwtExtractor


fun Route.configureApiV1BaseRoute() {
    get<ApiV1> {
        call.respond(HttpStatusCode.OK, hashMapOf("status" to "ok"))
    }
}

fun Route.configureUserRoutes(userService: UserService) {
    getUsersRoute(userService)
    createUserRoute(userService)
    getUserById(userService)
}

private fun Route.getUsersRoute(userService: UserService) {
    authenticate(JwtAuthExistUserKey) {
        get<ApiV1.Users> { params ->
            val page = params.page ?: 1
            val size = params.pageSize ?: 25

            if (page <= 0 || size < 0) {
                call.respond(
                    HttpStatusCode.BadRequest,
                    hashMapOf("error" to "Params must be greater than zero.")
                )

                return@get
            }

            val command = Commands.GetUsersPaging(page, size)
            userService.getUsersPaging(command)
                .onFailure { e -> call.respond(HttpStatusCode.InternalServerError, e.message.toString()) }
                .onSuccess { users -> call.respond(users) }
        }
    }
}

fun Route.createUserRoute(userService: UserService) {
    authenticate(JwtAuthNotExistUserKey) {
        post<ApiV1.Users> {
            val login = JwtExtractor.extractLoginFromRequest(call.request)

            if (login == null) {
                call.respond(HttpStatusCode.Unauthorized)

                return@post
            }

            val getCommand = Commands.GetUserByLogin(login)
            userService.getUserByLogin(getCommand)
                .onFailure { e ->
                    call.respond(HttpStatusCode.InternalServerError, e.message.orEmpty())
                }
                .onSuccess { usr ->
                    if (usr != null) {
                        call.respond(
                            HttpStatusCode.Conflict,
                            hashMapOf("error" to "User already exist.")
                        )
                        return@post
                    }
                }

            val createCommand = Commands.CreateUser(login)
            userService.createUser(createCommand)
                .onFailure { e ->
                    call.respond(HttpStatusCode.InternalServerError, e.message.orEmpty())
                }
                .onSuccess { usr ->
                    call.respond(
                        HttpStatusCode.OK,
                        hashMapOf("id" to usr.id)
                    )
                }
        }
    }
}

private fun Route.getUserById(userService: UserService) {
    authenticate(JwtAuthExistUserKey) {
        get<ApiV1.Users.Id> { params ->
            try {
                val command = Commands.GetUserById(params.id)
                userService.getUserById(command)
                    .onFailure { e ->
                        when(e) {
                            is IllegalArgumentException -> call.respond(
                                HttpStatusCode.BadRequest,
                                HttpStatusCode.BadRequest.description
                            )
                            else -> call.respond(HttpStatusCode.InternalServerError, e.message.orEmpty())
                        }
                    }
                    .onSuccess { usr ->
                        if (usr == null) {
                            call.respond(HttpStatusCode.NotFound)
                            return@get
                        }

                        call.respond(
                            HttpStatusCode.OK,
                            hashMapOf("user" to usr)
                        )
                    }
            } catch (e: Exception) {
                call.respond(
                    HttpStatusCode.BadRequest,
                    hashMapOf("error" to HttpStatusCode.BadRequest.description)
                )
            }
        }
    }
}