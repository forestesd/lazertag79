package com.example.comon.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

data class TaggerInfo(
    val taggerId: Int,
    var teamId: Int,
    val damageIndex: Int,
    val reloadTime: Int,
    val shockTime: Int,
    val invulnerabilityTime: Int,
    val fireSpeed: Int,
    val firePower: Int,
    val maxPatrons: Int,
    val maxPatronsForGame: Int,
    val maxHealth: Int,
//    val caption: String,
    val ip: String,
//    val chipId: String,
    var playerName: String,
    val taggerCharge: Int,
    val taggerChargeColor: BatteryColor,
    val bandageCharge: Int,
    val bandageChargeColor: BatteryColor,
    val isAutoReload: Boolean,
    val isFriendlyFire: Boolean,
    val isBtConnected: Boolean,
    val status: Int,
    val fireMode: Int,
    val volume: Int
)

@Serializable
@SerialName("config")
data class TaggerRes(
    val taggerId: Int,
    var teamId: Int,
    val damageIndex: Int,
    val reloadTime: Int,
    val shockTime: Int,
    val invulnerabilityTime: Int,
    val fireSpeed: Int,
    val firePower: Int,
    val maxPatrons: Int,
    val maxPatronsForGame: Int,
    val maxHealth: Int,
//    val caption: String,
    val ip: String,
//    val chipId: String,
    val isAutoReload: Boolean,
    val isFriendlyFire: Boolean,
    val isBtConnected: Boolean,
    val status: Int,
    val fireMode: Int,
    val volume: Int
):TaggerData

enum class BatteryColor{
    Green, Yellow, Red
}

@Serializable
@SerialName("game")
data class TaggerInfoGameRes(
    val taggerId: Int,
    val teamId: Int,
    val patronsForGame: Int,
    val health: Int,
    val shotByTaggerId: Int,
): TaggerData

@Serializable
data class TaggerResponse(
    val data: TaggerData
)


@Serializable
sealed interface TaggerData

data class TaggerInfoGame(
    val taggerId: Int,
    val teamId: Int,
    val taggerName: String,
    val patrons: Int,
    val health: Int,
    val shotByTaggerId: Int,
    val healthBarFill: Float
)