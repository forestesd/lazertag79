package com.example.mainscreen.domain.useCases

import com.example.mainscreen.domain.models.TeamModel
import com.example.mainscreen.domain.repository.TeamsRepositoryInterface
import javax.inject.Inject

class ChangeTeamNameUseCase @Inject constructor(
    private val teamsRepositoryInterface: TeamsRepositoryInterface
) {
    operator fun invoke(team: TeamModel?) = teamsRepositoryInterface.changeTeamName(team)
}