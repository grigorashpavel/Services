package ru.pasha.domain.repositories

import ru.pasha.domain.entities.User


interface UserRepository {
    suspend fun getUsers(): List<User>
}