package com.example.comon.server.domain.useCases

import com.example.comon.server.domain.repository.ServerRepositoryInterface
import javax.inject.Inject

class TaggersInfoUseCase @Inject constructor(
    private val repository: ServerRepositoryInterface
) {
    operator fun invoke()= repository.taggerData
}