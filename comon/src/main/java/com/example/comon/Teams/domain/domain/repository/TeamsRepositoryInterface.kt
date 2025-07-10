package com.example.comon.Teams.domain.domain.repository

import com.example.comon.Teams.domain.domain.models.TeamModel
import kotlinx.coroutines.flow.StateFlow

interface TeamsRepositoryInterface {
    val teams: StateFlow<List<TeamModel>>

    fun changeTeamName(team: TeamModel?)
}