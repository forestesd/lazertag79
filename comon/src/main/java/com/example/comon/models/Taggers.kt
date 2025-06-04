package com.example.comon.models

import kotlinx.serialization.Serializable

@Serializable
data class TaggerInfo(
    val taggerId: Int,
    val teamId: Int,
    val dmgIndex: Int,
    val reloadTime: Int,
    val shockTime: Int,
    val invulnTime: Int,
    val fireSpeed:Int,
    val firePower: Int,
    val maxPatrons: Int,
    val maxHealth: Int,
    val caption: String,
    val ip: String,
    val chipId: String,
    val autoreload: Boolean,
    val friendlyFire: Boolean,
    val btConnected: Int,
    val status: Int,
    val fireMode: Int
)
