package com.example.comon.game.domain.models

import java.time.Duration

data class Game(
    val gameTime: Duration,
    val timeBeforeStart: Duration,
    val isGameStart: Boolean,
    val friendlyFireMode: Boolean
)

data class GameConfig(
    val gameTime: Long,
    val timeBeforeStart: Long,
)
data class GameConfigLocalTime(
    val gameTime: Duration,
    val timeBeforeStart: Duration,
)


