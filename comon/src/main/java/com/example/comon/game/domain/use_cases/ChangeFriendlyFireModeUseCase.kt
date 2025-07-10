package com.example.comon.game.domain.use_cases

import com.example.comon.game.domain.GameRepositoryInterface
import com.example.comon.models.TaggerInfo
import javax.inject.Inject

class ChangeFriendlyFireModeUseCase @Inject constructor(
    private val gameRepository: GameRepositoryInterface
) {
    suspend operator fun invoke(
        friendlyFireMode: Boolean,
        taggers: List<TaggerInfo>
    ) = gameRepository.changeFriendlyFireMode(
        friendlyFireMode = friendlyFireMode,
        taggers = taggers
    )
}