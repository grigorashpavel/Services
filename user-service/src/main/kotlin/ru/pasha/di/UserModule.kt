package ru.pasha.di

import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import ru.pasha.data.repositories.UserRepositoryImpl
import ru.pasha.database.DatabaseService
import ru.pasha.domain.repositories.UserRepository
import ru.pasha.services.UserService
import ru.pasha.services.UserServiceImpl


val userModule = module {
    singleOf(::DatabaseService)
    singleOf(::UserRepositoryImpl) { bind<UserRepository>() }
    singleOf(::UserServiceImpl) { bind<UserService>() }
}