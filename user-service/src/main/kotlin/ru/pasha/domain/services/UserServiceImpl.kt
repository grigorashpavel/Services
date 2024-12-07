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
}