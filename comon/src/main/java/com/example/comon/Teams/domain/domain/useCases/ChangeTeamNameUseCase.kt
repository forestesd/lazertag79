package com.example.comon.Teams.domain.domain.useCases

import com.example.comon.Teams.domain.domain.models.TeamModel
import com.example.comon.Teams.domain.domain.repository.TeamsRepositoryInterface
import javax.inject.Inject

class ChangeTeamNameUseCase @Inject constructor(
    private val teamsRepositoryInterface: TeamsRepositoryInterface
) {
    operator fun invoke(team: TeamModel?) = teamsRepositoryInterface.changeTeamName(team)
}