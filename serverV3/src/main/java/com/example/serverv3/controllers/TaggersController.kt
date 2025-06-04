package com.example.serverv3.controllers

import android.util.Log
import com.example.comon.models.TaggerInfo
import com.example.comon.server.domain.useCases.ConnectTaggerUseCase
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.ApplicationCall
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class TaggersController @Inject constructor(
    private val call: ApplicationCall,
    private val connectTaggerUseCase: ConnectTaggerUseCase
) {
    suspend fun connectTagger() {
        try {
            val res = call.receive<TaggerInfo>()
            Log.d("SERVER", res.toString())

            withContext(Dispatchers.Main) {
                connectTaggerUseCase.invoke(res)
            }
            call.respond(HttpStatusCode.OK, "OK")
        } catch (e: Exception) {
            Log.d("SERVER", e.toString())
        }
    }
}