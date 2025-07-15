package com.example.mainscreen.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.comon.models.TaggerInfo
import com.example.comon.server.domain.useCases.ChangeTaggerInfoUseCase
import com.example.comon.server.domain.useCases.ChangeTeamUseCase
import com.example.comon.server.domain.useCases.TaggersInfoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ServerViewModel @Inject constructor(
    taggerInfoUseCase: TaggersInfoUseCase,
    private val changeTeamUseCase: ChangeTeamUseCase,
    private val changeTaggerInfoUseCase: ChangeTaggerInfoUseCase
) : ViewModel() {

    val taggerData: StateFlow<List<TaggerInfo>> = taggerInfoUseCase.invoke()

    private val _onDragTaggerId = MutableStateFlow<Int?>(null)
    val onDragTaggerId: StateFlow<Int?> = _onDragTaggerId


    fun changeTeam(tagger: TaggerInfo, team: Int) {
        viewModelScope.launch {
            changeTeamUseCase.invoke(tagger, team)
        }
    }

    fun onDrag(taggerId: Int?) {
        _onDragTaggerId.value = taggerId
    }

    fun changeTaggerInfo(taggers: List<TaggerInfo>) {
        viewModelScope.launch {
            changeTaggerInfoUseCase.invoke(taggers)

        }
    }
}