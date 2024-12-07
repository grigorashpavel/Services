package ru.pasha.services

import ru.pasha.domain.commands.Commands
import ru.pasha.routing.dto.UserDto


interface UserService {
    suspend fun getUsers(command: Commands.GetUsers): Result<List<UserDto>>
    suspend fun getUserByLogin(command: Commands.GetUserByLogin): Result<UserDto?>
    suspend fun getUserById(command: Commands.GetUserById): Result<UserDto?>
    suspend fun createUser(command: Commands.CreateUser): Result<UserDto>
    suspend fun getUsersPaging(command: Commands.GetUsersPaging): Result<List<UserDto>>
}