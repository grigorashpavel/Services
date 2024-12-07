package ru.pasha.domain.commands


sealed class Commands {
    object GetUsers: Commands()
    data class GetUserByLogin(val login: String): Commands()
    data class CreateUser(val login: String): Commands()
}