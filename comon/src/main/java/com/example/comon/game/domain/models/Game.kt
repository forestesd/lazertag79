package com.example.comon.game.domain.models

import kotlinx.serialization.Serializable
import java.time.LocalTime

data class Game(
    val gameTime: LocalTime,
    val timeBeforeStart: LocalTime,
    val isGameStart: Boolean,
    val friendlyFireMode: Boolean
)

data class GameConfig(
    val gameTime: String,
    val timeBeforeStart: String,
)
data class GameConfigLocalTime(
    val gameTime: LocalTime,
    val timeBeforeStart: LocalTime,
)

@Serializable
data class TaggerInfoGame(
    val taggerId: Int,
    val teamId: Int,
    val patrons: Int,
    val health: Int,
    val shotByTaggerId: Int,
)
