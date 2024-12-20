package ru.pasha.data.repositories

import ru.pasha.data.mappers.toEntity
import ru.pasha.database.DatabaseService
import ru.pasha.database.entities.UserDao
import ru.pasha.database.entities.UserTable
import ru.pasha.domain.entities.User
import ru.pasha.domain.repositories.UserRepository
import java.util.*


class UserRepositoryImpl(
    private val database: DatabaseService
): UserRepository {
    override suspend fun getUsers(): List<User> = database.query {
        UserDao.all().toList().map { dao -> dao.toEntity() }
    }

    override suspend fun getUsers(offset: Long, size: Int): List<User> = database.query {
        UserDao.all()
            .limit(size)
            .offset(offset)
            .map { it.toEntity() }
    }

    override suspend fun getUserByLogin(login: String): User? = database.query {
        UserDao.find { UserTable.login eq login }.firstOrNull()?.toEntity()
    }

    override suspend fun getUserById(id: UUID): User? = database.query {
        UserDao.findById(id)?.toEntity()
    }

    override suspend fun createUser(login: String): User = database.query {
        UserDao.new { this.login = login }.toEntity()
    }
}