package ru.pasha.domain.services

import ru.pasha.domain.commands.Commands
import ru.pasha.routing.dto.UserDto


interface UserService {
    suspend fun getUsers(command: Commands.GetUsers): Result<List<UserDto>>
}