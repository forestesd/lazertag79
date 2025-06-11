package com.example.comon.game.domain.use_cases

import com.example.comon.game.domain.GameRepositoryInterface
import java.time.LocalTime
import javax.inject.Inject

class ChangeTimeBeforeStartUseCase @Inject constructor(
    private val gameRepositoryInterface: GameRepositoryInterface
) {
    suspend operator fun invoke(time: LocalTime) =
        gameRepositoryInterface.changeTimeBeforeStart(time)
}