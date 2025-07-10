package com.example.comon.server.domain.useCases

import com.example.comon.models.TaggerInfo
import com.example.comon.server.data.ServerRepository
import com.example.comon.server.domain.repository.ServerRepositoryInterface
import javax.inject.Inject

class ChangeTaggerInfoUseCase @Inject constructor(
    private val serverRepository: ServerRepositoryInterface
) {
    suspend operator fun invoke(taggers: List<TaggerInfo>) =
        serverRepository.changeTaggerInfo(taggers)
}