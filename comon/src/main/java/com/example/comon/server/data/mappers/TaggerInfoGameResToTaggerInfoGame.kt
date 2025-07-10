package com.example.comon.server.data.mappers

import com.example.comon.models.TaggerInfo
import com.example.comon.models.TaggerInfoGame
import com.example.comon.models.TaggerInfoGameRes

fun taggerInfoGameResToTaggerInfoGame(
    taggerInfo: TaggerInfo,
    taggerGameRes: TaggerInfoGameRes,
    taggerInfoGame: TaggerInfoGame?
) = TaggerInfoGame(
    taggerId = taggerGameRes.taggerId,
    teamId = taggerGameRes.teamId,
    taggerName = taggerInfo.playerName,
    patrons = taggerGameRes.patronsForGame,
    health = taggerGameRes.health,
    shotByTaggerId = taggerGameRes.shotByTaggerId,
    healthBarFill = taggerGameRes.health.toFloat() / taggerInfo.maxHealth.toFloat(),
    kills = taggerInfoGame?.kills ?:0,
    deaths = taggerInfoGame?.deaths ?: 0

)