package com.example.comon.game.data

import android.content.Context
import com.example.comon.game.domain.GameRepositoryInterface
import com.example.comon.game.domain.models.Game
import com.example.comon.game.domain.models.GameConfig
import com.example.comon.game.domain.models.GameConfigLocalTime
import com.google.gson.Gson
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.io.File
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject

class GameRepository @Inject constructor(
    private val context: Context
) : GameRepositoryInterface {
    private val _game = MutableStateFlow(getGameDefault())

    private fun getGameDefault(): Game {
        val time = loadGameConfig()
        return Game(
            gameTime = time.gameTime,
            timeBeforeStart = time.timeBeforeStart,
            isGameStart = false
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

        saveGameConfig(
            GameConfig(
                gameTime = time.toString(),
                timeBeforeStart = _game.value.timeBeforeStart.toString()
            )
        )
    }

    override suspend fun changeTimeBeforeStart(time: LocalTime) {
        _game.value = _game.value.copy(timeBeforeStart = time)

        saveGameConfig(
            GameConfig(
                gameTime = _game.value.gameTime.toString(),
                timeBeforeStart = time.toString()
            )
        )
    }

    private fun saveGameConfig(config: GameConfig) {
        val file = File(context.filesDir, "config.json")
        val writer = file.writer()
        Gson().toJson(config, writer)
        writer.close()
    }
}