package com.example.comon.server.domain.repository

import com.example.comon.models.TaggerInfo
import kotlinx.coroutines.flow.StateFlow

interface ServerRepositoryInterface {
    val taggerData: StateFlow<List<TaggerInfo?>>

   suspend fun connectTagger(taggerInfo: TaggerInfo)

    fun changeTeam(tagger: TaggerInfo?, team: Int)
}