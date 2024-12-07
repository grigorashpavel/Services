package ru.pasha.domain.commands


sealed class Commands {
    data object GetUsers: Commands()
    data class GetUserById(val id: String): Commands()
    data class GetUsersPaging(val page: Int, val size: Int): Commands()
    data class GetUserByLogin(val login: String): Commands()
    data class CreateUser(val login: String): Commands()
}