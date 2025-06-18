package com.example.comon.game.data

import android.util.Log
import com.example.comon.models.TaggerInfo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import javax.inject.Inject

class WebSocketManager @Inject constructor(

) {
    private var webSocket: WebSocket? = null
    private val activeSockets = mutableMapOf<String, WebSocket>()

    private val _incomingMessages = MutableStateFlow<String?>(null)
    val incomingMessages: StateFlow<String?> = _incomingMessages

    fun connect(url: String) {
        val client = OkHttpClient()
        val request = Request.Builder()
            .url(url)
            .build()

        val listener = object : WebSocketListener() {
            override fun onOpen(webSocket: WebSocket, response: Response) {
                webSocket.send("Trigger async")
            }

            override fun onMessage(webSocket: WebSocket, text: String) {
                Log.d("WEB SOCKET", text)
                _incomingMessages.value = text
            }

            override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
                Log.d("WEB SOCKET", "Code: $code \n $reason")
            }

            override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
            }
        }

        webSocket = client.newWebSocket(request, listener)
    }

    private fun handleMessage(message: String) {

    }

    fun disconnect() {
        webSocket?.close(1000, "Закрыто пользователем")
    }

//    private fun reconnect(ip: String) {
//        // Простая реализация переподключения
//        CoroutineScope(Dispatchers.IO).launch {
//            delay(5000)
//            if (activeSockets.containsKey(ip)) {
//                Log.d("WEBSOCKET", "Reconnecting to $ip...")
//                startWebSocketSubscribe(listOf(TaggerInfo(ip)))
//            }
//        }
//    }
}