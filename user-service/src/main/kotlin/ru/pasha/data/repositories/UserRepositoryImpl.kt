package ru.pasha.data.repositories

import ru.pasha.data.mappers.toEntity
import ru.pasha.database.DatabaseService
import ru.pasha.database.entities.UserDao
import ru.pasha.domain.entities.User
import ru.pasha.domain.repositories.UserRepository


class UserRepositoryImpl(
    private val database: DatabaseService
): UserRepository {
    override suspend fun getUsers(): List<User> = database.query {
        UserDao.all().toList().map { dao -> dao.toEntity() }
    }
}