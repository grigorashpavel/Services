package ru.pasha.database

import Config
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.transactions.transaction
import ru.pasha.database.entities.UserTable


class DatabaseService {
    private val database: Database by lazy {
        Database.connect(
            url = Config.DB_URL,
            driver = "org.postgresql.Driver",
            user = Config.DB_USERNAME,
            password = Config.DB_PASSWORD)
    }

    init{
        init()
    }

    private fun init() {
        val tables = listOf(UserTable)
        runBlocking { createTablesIfNeeded(tables) }
    }

    private suspend fun createTablesIfNeeded(tables: List<Table>) {
        withContext(Dispatchers.IO) {
            launch {
                transaction(database) {
                    SchemaUtils.create(tables = tables.toTypedArray())
                }
            }
        }
    }

    suspend fun <T> query(block: suspend () -> T): T =
        newSuspendedTransaction(Dispatchers.IO, database) { block() }
}