package com.example.comon.server.domain.useCases

import com.example.comon.models.TaggerInfo
import com.example.comon.server.domain.repository.ServerRepositoryInterface
import javax.inject.Inject

class ConnectTaggerUseCase @Inject constructor(
    private val repository: ServerRepositoryInterface
) {
    operator fun invoke(taggerInfo: TaggerInfo) = repository.connectTagger(
        taggerInfo = taggerInfo
    )
}