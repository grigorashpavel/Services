package ru.pasha.domain.services

import ru.pasha.data.mappers.toDto
import ru.pasha.domain.commands.Commands
import ru.pasha.domain.repositories.UserRepository
import ru.pasha.routing.dto.UserDto


class UserServiceImpl(
    private val userRepository: UserRepository
): UserService {
    override suspend fun getUsers(command: Commands.GetUsers): Result<List<UserDto>> = runCatching {
        userRepository.getUsers().map { it.toDto() }
    }

    override suspend fun getUserByLogin(command: Commands.GetUserByLogin): Result<UserDto?> = runCatching {
        userRepository.getUserByLogin(command.login)?.toDto()
    }

    override suspend fun createUser(command: Commands.CreateUser): Result<UserDto> = runCatching {
        userRepository.createUser(command.login).toDto()
    }

    override suspend fun getUsersPaging(command: Commands.GetUsersPaging): Result<List<UserDto>> = runCatching {
        println(command)
        val offset: Long = (command.page - 1).toLong() * command.size

        userRepository.getUsers(offset, command.size).map { it.toDto() }
    }
}