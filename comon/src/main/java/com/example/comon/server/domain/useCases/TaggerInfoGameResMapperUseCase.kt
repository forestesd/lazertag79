package com.example.comon.server.domain.useCases

import com.example.comon.models.TaggerInfoGameRes
import com.example.comon.server.domain.repository.ServerRepositoryInterface
import javax.inject.Inject

class TaggerInfoGameResMapperUseCase @Inject constructor(
    private val serverRepositoryInterface: ServerRepositoryInterface
) {
    suspend operator fun invoke(taggerInfoGameRes: TaggerInfoGameRes) =
        serverRepositoryInterface.taggerInfoGameResMapper(taggerInfoGameRes)
}