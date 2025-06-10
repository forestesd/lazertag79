package com.example.mainscreen.presentation

import androidx.lifecycle.ViewModel
import com.example.comon.models.TaggerInfo
import com.example.comon.server.domain.ServerViewModelInterface
import com.example.comon.server.domain.useCases.ChangeTeamUseCase
import com.example.comon.server.domain.useCases.ConnectTaggerUseCase
import com.example.comon.server.domain.useCases.TaggersInfoUseCAse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class ServerViewModel @Inject constructor(
    taggerInfoUseCase: TaggersInfoUseCAse,
    private val connectTaggerUseCase: ConnectTaggerUseCase,
    private val changeTeamUseCase: ChangeTeamUseCase
) : ViewModel(), ServerViewModelInterface {

    val taggerData: StateFlow<List<TaggerInfo?>> = taggerInfoUseCase.invoke()

    private val _onDragTaggerId = MutableStateFlow<Int?>(null)
    val onDragTaggerId: StateFlow<Int?> = _onDragTaggerId


    fun changeTeam(tagger: TaggerInfo?, team: Int) {
        changeTeamUseCase.invoke(tagger, team)
    }

    fun onDrag(taggerId: Int?) {
        _onDragTaggerId.value = taggerId
    }


}