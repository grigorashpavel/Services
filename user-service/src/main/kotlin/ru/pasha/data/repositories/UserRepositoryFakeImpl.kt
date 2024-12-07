package ru.pasha.data.repositories

import ru.pasha.domain.entities.User
import ru.pasha.domain.repositories.UserRepository
import java.util.*


class UserRepositoryFakeImpl: UserRepository {
    private val users = List(20) { User(id = UUID.randomUUID(), login = generateRandomString()) }

    override suspend fun getUsers(): List<User> = users
    override suspend fun getUserByLogin(login: String): User? = users.find { usr -> usr.login == login }
}

private fun generateRandomString(): String {
    val length = kotlin.random.Random.nextInt(5, 26)
    val chars = ('a'..'z') + ('A'..'Z') + ('0'..'9')
    return (1..length)
        .map { kotlin.random.Random.nextInt(0, chars.size) }
        .map(chars::get)
        .joinToString("")
}