package com.example.serverv3

import com.example.comon.server.domain.useCases.ConnectTaggerUseCase
import io.ktor.server.application.Application
import io.ktor.server.cio.CIO
import io.ktor.server.engine.embeddedServer

fun startServer(host: String = "0.0.0.0", port: Int = 8080, connectTaggerUseCase: ConnectTaggerUseCase) {
    val embedded = embeddedServer(
        CIO,
        port = port,
        host = host,
        module = { module(connectTaggerUseCase) }
    )
    embedded.start(wait = false)
}


fun Application.module(connectTaggerUseCase: ConnectTaggerUseCase) {
    configureSerialization()
    configureRouting(connectTaggerUseCase)
}
