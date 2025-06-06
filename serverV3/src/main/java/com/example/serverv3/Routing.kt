package com.example.serverv3

import com.example.comon.server.domain.useCases.ConnectTaggerUseCase
import com.example.serverv3.controllers.TaggersController
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.Application
import io.ktor.server.response.respond
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.server.routing.routing


fun Application.configureRouting(connectTaggerUseCase: ConnectTaggerUseCase) {
    routing {
        post("/api/taggers") {
            TaggersController(
                call = call,
                connectTaggerUseCase = connectTaggerUseCase
            ).connectTagger()
        }

        get("/") {
            call.respond(HttpStatusCode.OK, "Hello, World")
        }
    }
}
