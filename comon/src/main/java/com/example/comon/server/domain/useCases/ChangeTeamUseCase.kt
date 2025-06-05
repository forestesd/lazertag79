package com.example.comon.server.domain.useCases

import com.example.comon.models.TaggerInfo
import com.example.comon.server.domain.repository.ServerRepositoryInterface
import javax.inject.Inject


class ChangeTeamUseCase @Inject constructor(
    private val repository: ServerRepositoryInterface
) {
    operator fun invoke(tagger: TaggerInfo?, team: Int) = repository.changeTeam(tagger, team)
}