package com.example.comon.game.data

import android.util.Log
import com.example.comon.models.TaggerData
import com.example.comon.models.TaggerInfoGame
import com.example.comon.models.TaggerInfoGameRes
import com.example.comon.models.TaggerRes
import com.example.comon.server.domain.useCases.ConnectTaggerUseCase
import com.example.comon.server.domain.useCases.TaggerInfoGameResMapperUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic
import kotlinx.serialization.modules.subclass
import org.java_websocket.WebSocket
import org.java_websocket.framing.Framedata
import org.java_websocket.handshake.ClientHandshake
import org.java_websocket.server.WebSocketServer
import java.net.InetSocketAddress
import javax.inject.Inject
import javax.inject.Provider

class WebSocketServer @Inject constructor(
    port: Int,
    private val connectTaggerUseCaseProvider: Provider<ConnectTaggerUseCase>,
    private val taggerInfoGameResMapperUseCase: Provider<TaggerInfoGameResMapperUseCase>
) : WebSocketServer(InetSocketAddress(port)) {

    private val _incomingMessages = MutableStateFlow<List<TaggerInfoGame>>(emptyList())
    val incomingMessages: StateFlow<List<TaggerInfoGame>> = _incomingMessages

    private val connectTaggerUseCase get() = connectTaggerUseCaseProvider.get()

    private val connectionsByIp = mutableMapOf<String, WebSocket>()

    private val json = Json {
        classDiscriminator = "type"
        ignoreUnknownKeys = true
        serializersModule = SerializersModule {
            polymorphic(TaggerData::class) {
                subclass(TaggerInfoGameRes::class)
                subclass(TaggerRes::class)
            }
        }
    }

    private val coroutineScope = CoroutineScope(Dispatchers.IO)


    override fun onOpen(conn: WebSocket?, handshake: ClientHandshake?) {
        val ip = conn?.remoteSocketAddress?.address?.hostAddress
        if (ip != null) {
            connectionsByIp[ip] = conn
            Log.w("WS_SERVER", "New connection from IP: $ip")
        }
        conn?.send("Welcome ESP32!")
    }

    override fun onStart() {
        Log.w("WS_SERVER", "Server started on port: $port")
    }

    override fun onWebsocketPing(conn: WebSocket?, f: Framedata?) {
        Log.w("WS_SERVER", "Ping received")
        super.onWebsocketPing(conn, f)
    }

    override fun onMessage(conn: WebSocket?, message: String) {
        Log.w("WS_SERVER", "Received: $message")
        try {
            when (val response = json.decodeFromString<TaggerData>(message)) {
                is TaggerInfoGameRes -> {
                    coroutineScope.launch {
                        val existing =
                            _incomingMessages.value.find { it.taggerId == response.taggerId }

                        taggerInfoGameResMapperUseCase.get().invoke(response).fold(
                            onSuccess = { mappedTagger ->
                                _incomingMessages.update { currentList ->
                                    if (existing != null) {
                                        currentList.filter { it.taggerId != response.taggerId } + mappedTagger
                                    } else {
                                        currentList + mappedTagger
                                    }
                                }
                            },
                            onFailure = { e ->
                                Log.e(
                                    "WS_SERVER",
                                    "Mapping failed for tagger ${response.taggerId}",
                                    e
                                )
                            }
                        )
                    }
                }

                is TaggerRes -> {
                    coroutineScope.launch {
                        connectTaggerUseCase.invoke(response)
                    }
                }

            }
        } catch (e: Exception) {
            Log.e("WEB SOCKET", "Failed to parse message", e)
        }
    }


    override fun onError(conn: WebSocket?, ex: java.lang.Exception?) {
        Log.e("WS_SERVER", "Error", ex)
    }

    override fun onClose(conn: WebSocket?, code: Int, reason: String?, remote: Boolean) {
        val ip = conn?.remoteSocketAddress?.address?.hostAddress
        if (ip != null) {
            connectionsByIp.remove(ip)
            Log.w("WS_SERVER", "Connection closed for IP: $ip")
        }
        Log.w("WS_SERVER", "Closed: $reason")
    }


    fun sendToIp(ip: String, data: TaggerData) {
        val conn = connectionsByIp[ip]
        if (conn != null && conn.isOpen) {
            val jsonStr = json.encodeToString(TaggerData.serializer(), data)
            conn.send(jsonStr)
        } else {
            Log.w("WS_SERVER", "Connection for IP $ip not found or closed")
        }
    }

    fun broadCastTypeOnly(data: String) {
        val jsonStr = """{"type":"$data"}"""
        synchronized(connections) {
            connections.forEach { conn ->
                if (conn.isOpen) {
                    conn.send(jsonStr)
                }
            }
        }
    }

}