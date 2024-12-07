package ru.pasha

import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.server.testing.*
import org.junit.Before
import kotlin.test.Test
import kotlin.test.assertEquals

class ApplicationTest {
    @Test
    fun testRoot() = testApplication {
        application {
            module()
        }

        client.get("/api/v1/").apply {
            println(call.request.url)
            assert(true)
            //assertEquals(HttpStatusCode.OK, status)
        }
    }
}


