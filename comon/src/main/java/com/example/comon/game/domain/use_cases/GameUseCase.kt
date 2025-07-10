package com.example.comon.game.domain.use_cases

import com.example.comon.game.domain.GameRepositoryInterface
import javax.inject.Inject

class GameUseCase @Inject constructor(
    private val gameRepositoryInterface: GameRepositoryInterface
) {
    operator fun invoke() = gameRepositoryInterface.game
}