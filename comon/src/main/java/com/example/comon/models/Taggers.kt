package com.example.comon.models

import kotlinx.serialization.Serializable

data class TaggerInfo(
    val taggerId: Int,
    var teamId: Int,
    val damageValue: Int,
    val reloadTime: Int,
    val shockTime: Int,
    val invulnerabilityTime: Int,
    val fireSpeed: Int,
    val firePower: Int,
    val maxPatrons: Int,
    val maxHealth: Int,
//    val caption: String,
    val ip: String,
//    val chipId: String,
    var playerName: String,
    val taggerCharge: Int,
    val taggerChargeColor: BatteryColor,
    val bandageCharge: Int,
    val bandageChargeColor: BatteryColor,
    val autoreload: Boolean,
    val friendlyFire: Boolean,
    val isBtConnected: Boolean,
    val status: Int,
    val fireMode: Int,
    val volume: Int
)

@Serializable
data class TaggerRes(
    val taggerId: Int,
    var teamId: Int,
    val damageValue: Int,
    val reloadTime: Int,
    val shockTime: Int,
    val invulnerabilityTime: Int,
    val fireSpeed: Int,
    val firePower: Int,
    val maxPatrons: Int,
    val maxHealth: Int,
//    val caption: String,
    val ip: String,
//    val chipId: String,
    val autoReload: Boolean,
    val friendlyFire: Boolean,
    val isBtConnected: Boolean,
    val status: Int,
    val fireMode: Int,
    val volume: Int
)

enum class BatteryColor{
    Green, Yellow, Red
}

