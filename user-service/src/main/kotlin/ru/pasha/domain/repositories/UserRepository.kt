package ru.pasha.domain.repositories

import ru.pasha.domain.entities.User
import java.util.UUID


interface UserRepository {
    suspend fun getUsers(): List<User>
    suspend fun getUsers(offset: Long, size: Int): List<User>
    suspend fun getUserByLogin(login: String): User?
    suspend fun getUserById(id: UUID): User?
    suspend fun createUser(login: String): User
}