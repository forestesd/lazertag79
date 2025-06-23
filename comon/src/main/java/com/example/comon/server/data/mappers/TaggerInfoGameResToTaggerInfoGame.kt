package com.example.comon.server.data.mappers

import com.example.comon.models.TaggerInfo
import com.example.comon.models.TaggerInfoGame
import com.example.comon.models.TaggerInfoGameRes

fun taggerInfoGameResToTaggerInfoGame(
    taggerInfo: TaggerInfo,
    taggerGameRes: TaggerInfoGameRes
) = TaggerInfoGame(
    taggerId = taggerGameRes.taggerId,
    teamId = taggerGameRes.teamId,
    taggerName = taggerInfo.playerName,
    patrons = taggerGameRes.patrons,
    health = taggerGameRes.health,
    shotByTaggerId = taggerGameRes.shotByTaggerId,
    healthBarFill = (taggerGameRes.health / taggerInfo.maxHealth).toFloat()
)