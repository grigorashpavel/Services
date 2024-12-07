package ru.pasha.database.entities

import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.UUIDTable
import org.jetbrains.exposed.sql.Column
import java.util.*


object UserTable : UUIDTable() {
    val login: Column<String> = varchar("login", 30)
}

class UserDao(id: EntityID<UUID>) : UUIDEntity(id) {
    companion object : UUIDEntityClass<UserDao>(UserTable)

    val userId: UUID get() = id.value
    var login by UserTable.login
}