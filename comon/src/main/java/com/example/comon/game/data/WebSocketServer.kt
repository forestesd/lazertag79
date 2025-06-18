package com.example.comon.game.data

import android.util.Log
import com.example.comon.game.domain.models.TaggerInfoGame
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.serialization.json.Json
import org.java_websocket.WebSocket
import org.java_websocket.handshake.ClientHandshake
import org.java_websocket.server.WebSocketServer
import java.net.InetSocketAddress
import javax.inject.Inject

class WebSocketServer @Inject constructor(
    port: Int
): WebSocketServer(InetSocketAddress(port)){

    private val _incomingMessages = MutableStateFlow<List<TaggerInfoGame>>(emptyList())
    val incomingMessages: StateFlow<List<TaggerInfoGame>> = _incomingMessages


    override fun onOpen(conn: WebSocket?, handshake: ClientHandshake?) {
        Log.d("WS_SERVER", "New connection from ${conn?.remoteSocketAddress}")
        conn?.send("Welcome ESP32!")
    }

    override fun onStart() {
        Log.d("WS_SERVER", "Server started on port: $port")
    }

    override fun onMessage(conn: WebSocket?, message: String) {
        Log.d("WS_SERVER", "Received: $message")
        try {
            val data = Json.decodeFromString<TaggerInfoGame>(message)

            _incomingMessages.value += data
        } catch (e: Exception) {
            Log.e("WEB SOCKET", "Failed to parse message", e)
        }
    }

    override fun onError(conn: WebSocket?, ex: java.lang.Exception?) {
        Log.e("WS_SERVER", "Error", ex)
    }

    override fun onClose(conn: WebSocket?, code: Int, reason: String?, remote: Boolean) {
        Log.d("WS_SERVER", "Closed: $reason")
    }
}