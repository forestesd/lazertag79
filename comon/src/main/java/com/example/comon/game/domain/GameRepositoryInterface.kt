package com.example.comon.game.domain

import com.example.comon.game.domain.models.Game
import kotlinx.coroutines.flow.StateFlow
import java.sql.Time
import java.time.LocalTime

interface GameRepositoryInterface {
    val game: StateFlow<Game>

    suspend fun changeGameTime(time: LocalTime)

    suspend fun changeTimeBeforeStart(time: LocalTime)
}