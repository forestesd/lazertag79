package com.example.comon.server.data.mappers

import com.example.comon.models.BatteryColor
import com.example.comon.models.TaggerInfo
import com.example.comon.models.TaggerRes

fun taggerResToTaggerInfo(
    taggerRes: TaggerRes
): TaggerInfo {
    val taggerCharge = 85
    val bandageCharge = 40
    return TaggerInfo(
        taggerId = taggerRes.taggerId,
        teamId = taggerRes.teamId,
        damageValue = taggerRes.damageValue,
        reloadTime = taggerRes.reloadTime,
        shockTime = taggerRes.shockTime,
        invulnerabilityTime = taggerRes.invulnerabilityTime,
        fireSpeed = taggerRes.fireSpeed,
        firePower = taggerRes.firePower,
        maxPatrons = taggerRes.maxPatrons,
        maxHealth = taggerRes.maxHealth,
//        caption = taggerRes.caption,
        ip = taggerRes.ip,
//        chipId = taggerRes.chipId,
        playerName = "Kotlin",
        taggerCharge = taggerCharge,
        taggerChargeColor = when (taggerCharge) {
            in 50..100 -> BatteryColor.Green
            in 30..49 -> BatteryColor.Yellow
            else -> BatteryColor.Red
        },
        bandageCharge = bandageCharge,
        bandageChargeColor = when (bandageCharge) {
            in 50..100 -> BatteryColor.Green
            in 30..49 -> BatteryColor.Yellow
            else -> BatteryColor.Red
        },
        autoreload = taggerRes.autoReload,
        friendlyFire = taggerRes.friendlyFire,
        isBtConnected = taggerRes.isBtConnected,
        status = taggerRes.status,
        fireMode = taggerRes.fireMode,
        volume = taggerRes.volume
    )
}