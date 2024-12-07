package ru.pasha.routing

import io.ktor.resources.*


@Resource(Paths.Base)
class ApiV1 {
    @Resource(Paths.Users)
    class Users(val parent: ApiV1)
}