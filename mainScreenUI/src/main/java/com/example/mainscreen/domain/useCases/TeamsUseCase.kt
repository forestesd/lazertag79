package com.example.mainscreen.domain.useCases

import com.example.mainscreen.domain.repository.TeamsRepositoryInterface
import javax.inject.Inject

class TeamsUseCase @Inject constructor(
    private val teamsRepositoryInterface: TeamsRepositoryInterface
) {
    operator fun invoke() = teamsRepositoryInterface.teams
}