package ru.pasha.domain.services

import ru.pasha.domain.commands.Commands
import ru.pasha.routing.dto.UserDto


interface UserService {
    suspend fun getUsers(command: Commands.GetUsers): Result<List<UserDto>>
    suspend fun getUserByLogin(commands: Commands.GetUserByLogin): Result<UserDto?>
    suspend fun createUser(command: Commands.CreateUser): Result<UserDto>
}