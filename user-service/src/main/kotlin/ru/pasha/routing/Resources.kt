package ru.pasha.routing

import io.ktor.resources.*
import kotlinx.serialization.SerialName


@Resource(Paths.Base)
class ApiV1 {
    @Resource(Paths.Users)
    class Users(
        val parent: ApiV1,
        val page: Int? = null,
        @SerialName("page_size") val pageSize: Int? = null,
    )
}