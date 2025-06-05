package com.example.mainscreen

import androidx.lifecycle.ViewModel
import com.example.comon.server.domain.ServerViewModelInterface
import com.example.comon.models.TaggerInfo
import com.example.comon.server.domain.useCases.ConnectTaggerUseCase
import com.example.comon.server.domain.useCases.TaggersInfoUseCAse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class ServerViewModel @Inject constructor(
    taggerInfoUseCase: TaggersInfoUseCAse,
    private val connectTaggerUseCase: ConnectTaggerUseCase
) : ViewModel(), ServerViewModelInterface {

    val taggerData: StateFlow<List<TaggerInfo?>> = taggerInfoUseCase.invoke()

    override fun update(newData: TaggerInfo) {
        connectTaggerUseCase.invoke(newData)
    }

}