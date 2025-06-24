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
import java.time.Duration
import javax.inject.Inject

class GameRepository @Inject constructor(
    @ApplicationContext private val context: Context,
    private val serviceFactory: UpdateTaggerServiceFactory,
    private val webSocketServer: WebSocketServer
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
        val res = Gson().fromJson(reader.readText(), GameConfig::class.java)
        Log.i("GAME CONFIG", res.toString())

        return GameConfigLocalTime(
            gameTime = Duration.ofSeconds(res.gameTime),
            timeBeforeStart = Duration.ofSeconds(res.timeBeforeStart)
        )
    }


    override val game: StateFlow<Game> = _game

    override suspend fun changeGameTime(duration: Duration) {
        _game.value = _game.value.copy(gameTime = duration)

        saveGameConfig(
            GameConfig(
                gameTime = duration.seconds,
                timeBeforeStart = _game.value.timeBeforeStart.seconds
            )
        )
        Log.d("GAME", game.value.gameTime.toString())

    }

    override suspend fun changeTimeBeforeStart(duration: Duration) {
        _game.value = _game.value.copy(timeBeforeStart = duration)

        Log.i("GAME CONFIG", duration.toString())

        saveGameConfig(
            GameConfig(
                gameTime = _game.value.gameTime.seconds,
                timeBeforeStart = duration.seconds
            )
        )
    }

    override suspend fun gameStart() {
        _game.value = _game.value.copy(isGameStart = true)
        webSocketServer.broadCastTypeOnly("start")
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
                        webSocketServer.sendToIp(tagger.ip, taggerInfoToTaggerRes(tagger))
                    } catch (e: Exception) {
                        Log.e("Change friendly fire mode", "Failed for ${tagger.ip}: $e")
                    }
                }
            }.awaitAll()
        }
    }

    private fun saveGameConfig(config: GameConfig) {
        val file = File(context.filesDir, "config.json")
        file.writeText(Gson().toJson(config))
    }
}