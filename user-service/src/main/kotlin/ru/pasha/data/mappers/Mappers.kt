package ru.pasha.data.mappers

import ru.pasha.database.entities.UserDao
import ru.pasha.domain.entities.User
import ru.pasha.routing.dto.UserDto


fun User.toDto() = UserDto(id = id.toString(), login = login)

fun UserDao.toEntity() = User(id = userId, login = login)