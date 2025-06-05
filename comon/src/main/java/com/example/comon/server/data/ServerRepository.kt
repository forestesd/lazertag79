package com.example.comon.server.data

import android.util.Log
import com.example.comon.models.TaggerInfo
import com.example.comon.server.domain.repository.ServerRepositoryInterface
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

class ServerRepository @Inject constructor() : ServerRepositoryInterface {
    private val _taggerData = MutableStateFlow<List<TaggerInfo?>>(emptyList())
    override val taggerData: StateFlow<List<TaggerInfo?>> = _taggerData

    override suspend fun connectTagger(taggerInfo: TaggerInfo) {
        _taggerData.value += taggerInfo
    }

    override fun changeTeam(tagger: TaggerInfo?, team: Int) {
        Log.d("SERVER", _taggerData.value.find { it?.taggerId == tagger?.taggerId }.toString())
        _taggerData.update { currentList ->
            currentList.map {
                if (it?.taggerId == tagger?.taggerId) it?.copy(teamId = team)
                else it
            }
        }
        Log.d("SERVER", _taggerData.value.find { it?.taggerId == tagger?.taggerId }.toString())
    }
}