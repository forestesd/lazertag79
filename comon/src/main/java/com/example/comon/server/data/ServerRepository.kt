package com.example.comon.server.data

import com.example.comon.models.TaggerInfo
import com.example.comon.server.domain.repository.ServerRepositoryInterface
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class ServerRepository @Inject constructor() : ServerRepositoryInterface {
    private val _taggerData = MutableStateFlow<List<TaggerInfo?>>(emptyList())
    override val taggerData: StateFlow<List<TaggerInfo?>> = _taggerData

    override fun connectTagger(taggerInfo: TaggerInfo) {
        _taggerData.value += taggerInfo
    }
}