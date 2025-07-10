package com.example.comon.server.domain.useCases

import com.example.comon.models.TaggerInfo
import com.example.comon.server.domain.repository.ServerRepositoryInterface
import javax.inject.Inject

class TaggerConfigUpdateUseCase @Inject constructor(
    private val serverRepositoryInterface: ServerRepositoryInterface
){
    suspend operator fun invoke(tagger: List<TaggerInfo>) = serverRepositoryInterface.taggerConfigUpdate(tagger)
}