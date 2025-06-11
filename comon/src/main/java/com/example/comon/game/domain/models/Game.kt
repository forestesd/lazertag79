package com.example.comon.game.domain.models

import java.time.LocalTime

data class Game(
    val gameTime: LocalTime,
    val timeBeforeStart: LocalTime,
    val isGameStart: Boolean
)

data class GameConfig(
    val gameTime: String,
    val timeBeforeStart: String,
)
data class GameConfigLocalTime(
    val gameTime: LocalTime,
    val timeBeforeStart: LocalTime,
)
