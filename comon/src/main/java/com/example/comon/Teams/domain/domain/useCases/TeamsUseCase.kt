package com.example.comon.Teams.domain.domain.useCases

import com.example.comon.Teams.domain.domain.repository.TeamsRepositoryInterface
import javax.inject.Inject

class TeamsUseCase @Inject constructor(
    private val teamsRepositoryInterface: TeamsRepositoryInterface
) {
    operator fun invoke() = teamsRepositoryInterface.teams
}