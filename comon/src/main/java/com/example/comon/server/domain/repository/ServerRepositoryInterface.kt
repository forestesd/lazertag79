package com.example.comon.server.domain.repository

import com.example.comon.models.TaggerInfo
import com.example.comon.models.TaggerInfoGame
import com.example.comon.models.TaggerInfoGameRes
import com.example.comon.models.TaggerRes
import kotlinx.coroutines.flow.StateFlow

interface ServerRepositoryInterface {
    val taggerData: StateFlow<List<TaggerInfo>>

    suspend fun connectTagger(taggerRes: TaggerRes)

    suspend fun changeTeam(tagger: TaggerInfo, team: Int)

    fun updateTaggerInfo(taggerInfo: TaggerInfo)

    suspend fun changeTaggerInfo(taggers: List<TaggerInfo>)

    suspend fun taggerInfoGameResMapper(
        taggerGameRes: TaggerInfoGameRes,
        taggerInfoGame: TaggerInfoGame?
    ): Result<TaggerInfoGame>

    suspend fun taggerConnectionClose(taggerInfo: TaggerInfo)

    suspend fun taggerConfigUpdate(tagger: List<TaggerInfo>)
}