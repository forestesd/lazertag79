package com.example.comon.game.domain.use_cases

import com.example.comon.game.domain.GameRepositoryInterface
import javax.inject.Inject

class GameStartUseCase @Inject constructor(
    private val gameRepository: GameRepositoryInterface
) {
    suspend operator fun invoke() = gameRepository.gameStart()
}