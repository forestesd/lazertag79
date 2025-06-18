package com.example.comon.game.domain.use_cases

import com.example.comon.game.domain.GameRepositoryInterface
import com.example.comon.models.TaggerInfo
import javax.inject.Inject

class StartWebSocketSubscribeUseCase @Inject constructor(
    private val gameRepositoryInterface: GameRepositoryInterface
) {
    suspend operator fun invoke(taggers: List<TaggerInfo>) =
        gameRepositoryInterface.startWebSocketSubscribe(taggers)
}