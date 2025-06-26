package com.example.comon.game.domain.use_cases

import com.example.comon.game.domain.GameRepositoryInterface
import javax.inject.Inject

class GameLogsUpdateUseCase @Inject constructor(
    private val gameRepositoryInterface: GameRepositoryInterface
) {
    suspend operator fun invoke(
        taggerName: String,
        shotByTaggerName: String,
        isKilled: Boolean
    ) = gameRepositoryInterface.gameLogsUpdate(taggerName, shotByTaggerName, isKilled)
}