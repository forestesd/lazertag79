package com.example.comon.server.data.mappers

import com.example.comon.models.TaggerInfo
import com.example.comon.models.TaggerRes

fun taggerInfoToTaggerRes(
    taggerInfo: TaggerInfo
) : TaggerRes {
    return taggerInfo.let {
        TaggerRes(
            taggerId = it.taggerId,
            teamId = it.teamId,
            damageIndex = it.damageIndex,
            reloadTime = it.reloadTime,
            shockTime = it.shockTime,
            invulnerabilityTime = it.invulnerabilityTime,
            fireSpeed = it.fireSpeed,
            firePower = it.firePower,
            maxPatrons = it.maxPatrons,
            maxHealth = it.maxHealth,
            ip = it.ip,
            autoReload = it.autoreload,
            friendlyFire = it.friendlyFire,
            isBtConnected = it.isBtConnected,
            status = it.status,
            fireMode = it.fireMode,
            volume = it.volume
        )
    }

}