package com.example.comon.game.domain.use_cases

import com.example.comon.game.domain.GameRepositoryInterface
import java.time.Duration
import java.time.LocalTime
import javax.inject.Inject

class ChangeGameTimeUseCase @Inject constructor(
    private val gameRepositoryInterface: GameRepositoryInterface
) {
    suspend operator fun invoke(duration: Duration) = gameRepositoryInterface.changeGameTime(duration)
}