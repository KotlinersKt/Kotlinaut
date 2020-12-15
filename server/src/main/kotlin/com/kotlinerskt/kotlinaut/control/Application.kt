package com.kotlinerskt.kotlinaut.control

import io.ktor.application.*
import io.ktor.features.*
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.routing.*

fun Application.main() {
    install(DefaultHeaders)
    install(CallLogging)
    install(CORS) {
        method(HttpMethod.Get)
        method(HttpMethod.Post)
        anyHost()
    }

    install(Routing) {
        get("/") {
            call.respondText("Hello Central", ContentType.Text.Plain)
        }
    }
}
