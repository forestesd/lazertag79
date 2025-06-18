package com.example.comon.game.data

import android.content.Context
import android.util.Log
import com.example.comon.factory.UpdateTaggerServiceFactory
import com.example.comon.game.domain.GameRepositoryInterface
import com.example.comon.game.domain.models.Game
import com.example.comon.game.domain.models.GameConfig
import com.example.comon.game.domain.models.GameConfigLocalTime
import com.example.comon.models.TaggerInfo
import com.example.comon.server.data.mappers.taggerInfoToTaggerRes
import com.google.gson.Gson
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.io.File
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject

class GameRepository @Inject constructor(
    @ApplicationContext private val context: Context,
    private val serviceFactory: UpdateTaggerServiceFactory,
    private val webSocketManager: WebSocketManager

) : GameRepositoryInterface {
    private var _game = MutableStateFlow(getGameDefault())

    private fun getGameDefault(): Game {
        val time = loadGameConfig()
        return Game(
            gameTime = time.gameTime,
            timeBeforeStart = time.timeBeforeStart,
            isGameStart = false,
            friendlyFireMode = false
        )
    }

    private fun loadGameConfig(): GameConfigLocalTime {
        val file = File(context.filesDir, "config.json")
        val reader = file.reader()
        val res = Gson().fromJson(reader, GameConfig::class.java)

        val formatter = DateTimeFormatter.ofPattern("HH:mm:ss")

        val gameTime: LocalTime = LocalTime.parse(res.gameTime, formatter)
        val timeBeforeStart: LocalTime = LocalTime.parse(res.timeBeforeStart, formatter)

        return GameConfigLocalTime(
            gameTime = gameTime,
            timeBeforeStart = timeBeforeStart
        )
    }


    override val game: StateFlow<Game> = _game

    override suspend fun changeGameTime(time: LocalTime) {
        _game.value = _game.value.copy(gameTime = time)
        val formatter = DateTimeFormatter.ofPattern("HH:mm:ss")

        saveGameConfig(
            GameConfig(
                gameTime = time.format(formatter),
                timeBeforeStart = _game.value.timeBeforeStart.toString()
            )
        )
        Log.d("GAME", game.value.gameTime.toString())

    }

    override suspend fun gameStart() {
        _game.value = _game.value.copy(isGameStart = true)
    }

    override suspend fun startWebSocketSubscribe(taggers: List<TaggerInfo>) {
        coroutineScope {
            taggers.filter { it.teamId != 0 }.map { tagger ->
                async {
                    try {
                        webSocketManager.connect("http://${tagger.ip}/ws")
                    } catch (e: Exception) {
                        Log.e("WEBSOCKET", "ip: ${tagger.ip} \n $e")
                    }
                }
            }.awaitAll()
        }
    }

    override suspend fun changeTimeBeforeStart(time: LocalTime) {
        _game.value = _game.value.copy(timeBeforeStart = time)

        val formatter = DateTimeFormatter.ofPattern("HH:mm:ss")

        saveGameConfig(
            GameConfig(
                gameTime = _game.value.gameTime.toString(),
                timeBeforeStart = time.format(formatter)
            )
        )
    }

    override suspend fun changeFriendlyFireMode(
        friendlyFireMode: Boolean,
        taggers: List<TaggerInfo>
    ) {
        _game.value = _game.value.copy(friendlyFireMode = friendlyFireMode)
        coroutineScope {
            taggers.map { tagger ->
                async {
                    try {
                        val service = serviceFactory.create(baseUrl = "http://${tagger.ip}/")
                        service.updateTaggerData(taggerInfoToTaggerRes(tagger.copy(friendlyFire = friendlyFireMode)))
                    } catch (e: Exception) {
                        Log.e("Change friendly fire mode", "Failed for ${tagger.ip}: $e")
                    }
                }
            }.awaitAll()
        }
    }

    private fun saveGameConfig(config: GameConfig) {
        val file = File(context.filesDir, "config.json")
        val writer = file.writer()
        Gson().toJson(config, writer)
        writer.close()
    }
}