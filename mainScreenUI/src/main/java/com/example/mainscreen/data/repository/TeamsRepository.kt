package com.example.mainscreen.data.repository

import com.example.comon.Teams.domain.domain.models.TeamModel
import com.example.comon.Teams.domain.domain.repository.TeamsRepositoryInterface
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

class TeamsRepository @Inject constructor() : TeamsRepositoryInterface {
    private val _teams = MutableStateFlow(
        listOf(
            TeamModel(
                teamName = "Красные",
                wins = 0,
                teamId = 1
            ),
            TeamModel(
                teamName = "Синие",
                wins = 0,
                teamId = 2
            )
        )
    )
    override val teams: StateFlow<List<TeamModel>> = _teams

    override fun changeTeamName(team: TeamModel?) {
        _teams.update { list ->
            list.map {
                if (it == team) team
                else it
            }
        }
    }
}