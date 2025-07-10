package com.example.comon.game.domain.use_cases

import com.example.comon.game.domain.GameRepositoryInterface
import javax.inject.Inject

class GameStopUseCase @Inject constructor(
    private val gameRepositoryInterface: GameRepositoryInterface
) {
    suspend operator fun invoke() = gameRepositoryInterface.gameStop()
}