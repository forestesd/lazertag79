package com.example.mainscreen.domain.repository

import com.example.comon.models.TaggerInfo
import com.example.mainscreen.domain.models.TeamModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

interface TeamsRepositoryInterface {
    val teams: StateFlow<List<TeamModel>>

    fun changeTeamName(team: TeamModel)
}