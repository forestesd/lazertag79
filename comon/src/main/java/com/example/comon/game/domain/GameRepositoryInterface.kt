package com.example.comon.game.domain

import com.example.comon.game.domain.models.Game
import com.example.comon.models.TaggerInfo
import kotlinx.coroutines.flow.StateFlow
import java.time.Duration

interface GameRepositoryInterface {
    val game: StateFlow<Game>

    suspend fun changeGameTime(duration: Duration)

    suspend fun changeTimeBeforeStart(duration: Duration)

    suspend fun changeFriendlyFireMode(friendlyFireMode: Boolean, taggers: List<TaggerInfo>)

    suspend fun gameStart()

    suspend fun gameStop()

    suspend fun gameLogsUpdate(taggerName: String, shotByTaggerName: String, isKilled: Boolean)

}