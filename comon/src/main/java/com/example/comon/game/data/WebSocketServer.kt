package com.example.comon.game.data

import android.util.Log
import com.example.comon.game.domain.use_cases.GameLogsUpdateUseCase
import com.example.comon.models.TaggerData
import com.example.comon.models.TaggerInfo
import com.example.comon.models.TaggerInfoGame
import com.example.comon.models.TaggerInfoGameRes
import com.example.comon.models.TaggerRes
import com.example.comon.server.domain.useCases.ConnectTaggerUseCase
import com.example.comon.server.domain.useCases.TaggerInfoGameResMapperUseCase
import com.example.comon.server.domain.useCases.TaggersInfoUseCase
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
    private val taggerInfoGameResMapperUseCase: Provider<TaggerInfoGameResMapperUseCase>,
    taggerInfoUseCase: Provider<TaggersInfoUseCase>,
    private val gameLogsUpdateUseCase: Provider<GameLogsUpdateUseCase>
) : WebSocketServer(InetSocketAddress(port)) {

    private val taggersInfo: StateFlow<List<TaggerInfo>> by lazy {
        taggerInfoUseCase.get().invoke()
    }

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
                        Log.d("WS_SERVER", "Received: ${response.health}")
                        taggerInfoGameResMapperUseCase.get().invoke(
                            response,
                            _incomingMessages.value.find { it.taggerId == response.taggerId }).fold(
                            onSuccess = { mappedTagger ->
                                _incomingMessages.update { currentList ->
                                    if (mappedTagger.shotByTaggerId != 0) {

                                        gameLogsUpdateUseCase.get().invoke(
                                            taggerName = taggersInfo.value.find { it.taggerId == mappedTagger.taggerId }?.taggerId.toString(),
                                            shotByTaggerName = taggersInfo.value.find { it.taggerId == mappedTagger.shotByTaggerId }?.taggerId.toString(),
                                            isKilled = mappedTagger.health == 0,
                                        )
                                        if (mappedTagger.health == 0) {
                                            val updatedKillsList = currentList.map { tagger ->
                                                if (tagger.taggerId == mappedTagger.shotByTaggerId) {
                                                    Log.d("KILLS", "Killed ${tagger.taggerId}")
                                                    tagger.copy(kills = tagger.kills + 1)
                                                } else tagger
                                            }

                                            val updatedFinalList = updatedKillsList.map { tagger ->
                                                if (tagger.taggerId == mappedTagger.taggerId) {
                                                    tagger.copy(
                                                        deaths = tagger.deaths + 1,
                                                        patrons = mappedTagger.patrons,
                                                        shotByTaggerId = mappedTagger.shotByTaggerId,
                                                        healthBarFill = mappedTagger.healthBarFill,
                                                        health = 0
                                                    )
                                                } else tagger
                                            }

                                            Log.d(
                                                "KILLS", "Killed ${
                                                    updatedFinalList.find { it.taggerId == mappedTagger.shotByTaggerId }?.kills
                                                }"
                                            )

                                            return@update updatedFinalList
                                        }


                                    }
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

    fun startGameBroadcast(data: String, time: Long) {
        val jsonStr = """{
            "type":"$data",
            "timeToStart":"$time"
            }""".trimMargin()
        synchronized(connections) {
            connections.forEach { conn ->
                if (conn.isOpen) {
                    conn.send(jsonStr)
                }
            }
        }
    }

}