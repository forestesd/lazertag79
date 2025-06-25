package com.example.comon.server.data

import android.util.Log
import com.example.comon.factory.UpdateTaggerServiceFactory
import com.example.comon.game.data.WebSocketServer
import com.example.comon.models.TaggerInfo
import com.example.comon.models.TaggerInfoGame
import com.example.comon.models.TaggerInfoGameRes
import com.example.comon.models.TaggerRes
import com.example.comon.server.data.mappers.taggerInfoGameResToTaggerInfoGame
import com.example.comon.server.data.mappers.taggerInfoToTaggerRes
import com.example.comon.server.data.mappers.taggerResToTaggerInfo
import com.example.comon.server.domain.repository.ServerRepositoryInterface
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject
import kotlin.math.exp

class ServerRepository @Inject constructor(
    private val webSocketServer: WebSocketServer
) : ServerRepositoryInterface {
    private val _taggerData = MutableStateFlow<List<TaggerInfo>>(emptyList())
    override val taggerData: StateFlow<List<TaggerInfo>> = _taggerData

    override suspend fun connectTagger(taggerRes: TaggerRes) {
        val tagger = taggerResToTaggerInfo(taggerRes)
        if (_taggerData.value.find { it.taggerId == tagger.taggerId } == null) {
            _taggerData.value += tagger
        } else {
            _taggerData.update { list ->
                list.map {
                    if (it == tagger) tagger
                    else it
                }
            }
        }
    }

    override suspend fun taggerConnectionClose(taggerInfo: TaggerInfo) {
        _taggerData.update { list ->
            list.filter { it != taggerInfo }
        }
    }

    override suspend fun taggerInfoGameResMapper(taggerGameRes: TaggerInfoGameRes): Result<TaggerInfoGame> {
        return try {
            val taggerInfo = _taggerData.value.find { it.taggerId == taggerGameRes.taggerId }
                ?: throw IllegalArgumentException("Tagger with ID ${taggerGameRes.taggerId} not found")

            Result.success(
                taggerInfoGameResToTaggerInfoGame(
                    taggerInfo = taggerInfo,
                    taggerGameRes = taggerGameRes
                )
            )
        } catch (e: Exception) {
            Log.e("Tagger_Info_Game_Res_Mapper", e.toString())
            Result.failure(e)
        }
    }

    override fun updateTaggerInfo(taggerInfo: TaggerInfo) {
        _taggerData.update { list ->
            list.map {
                if (it == taggerInfo) taggerInfo
                else it
            }
        }
    }

    override suspend fun changeTaggerInfo(taggers: List<TaggerInfo>) {
        _taggerData.update {
            taggers
        }
    }

    override suspend fun changeTeam(tagger: TaggerInfo, team: Int) {
        _taggerData.update { currentList ->
            currentList.map {
                if (it.taggerId == tagger.taggerId) it.copy(teamId = team)
                else it
            }
        }
        try {
            webSocketServer.sendToIp(
                ip = tagger.ip,
                data = _taggerData.value.find { it.taggerId == tagger.taggerId }
                    ?.let { taggerInfo ->
                        taggerInfoToTaggerRes(taggerInfo)
                    } ?: throw IllegalArgumentException("Tagger not found in the list")
            )
        } catch (e: Exception) {
            Log.e("Change team", e.toString())
        }
    }
}