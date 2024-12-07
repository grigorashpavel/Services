package ru.pasha.data.repositories

import ru.pasha.domain.entities.User
import ru.pasha.domain.repositories.UserRepository
import java.util.*


class UserRepositoryFakeImpl: UserRepository {
    private val users = List(20) { User(id = UUID.randomUUID(), login = generateRandomString()) }.toMutableList()

    override suspend fun getUsers(): List<User> = users.toList()
    override suspend fun getUsers(offset: Long, size: Int): List<User> {
        val ofs = offset.toInt()
        if (ofs > users.lastIndex) {
            return emptyList()
        }

        val lastPosition = (ofs + size).coerceAtMost(users.size)

        return users.subList(ofs, lastPosition)
    }

    override suspend fun getUserByLogin(login: String): User? = users.find { usr -> usr.login == login }?.copy()
    override suspend fun getUserById(id: UUID): User? = users.find { it.id == id }

    override suspend fun createUser(login: String): User {
        val user = User(id = UUID.randomUUID(), login = login)
        users.add(user)

        return user
    }
}

private fun generateRandomString(): String {
    val length = kotlin.random.Random.nextInt(5, 26)
    val chars = ('a'..'z') + ('A'..'Z') + ('0'..'9')
    return (1..length)
        .map { kotlin.random.Random.nextInt(0, chars.size) }
        .map(chars::get)
        .joinToString("")
}