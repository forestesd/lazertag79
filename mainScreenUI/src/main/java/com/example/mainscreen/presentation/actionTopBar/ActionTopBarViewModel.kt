package com.example.mainscreen.presentation.actionTopBar

import androidx.lifecycle.ViewModel
import com.example.mainscreen.domain.models.TeamModel
import com.example.mainscreen.domain.useCases.ChangeTeamNameUseCase
import com.example.mainscreen.domain.useCases.TeamsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class ActionTopBarViewModel @Inject constructor(
    private val changeTeamNameUseCase: ChangeTeamNameUseCase,
    teamsUseCase: TeamsUseCase
) : ViewModel() {
    val teams: StateFlow<List<TeamModel>> = teamsUseCase.invoke()

    fun updateTeamName(team: TeamModel?) {
        changeTeamNameUseCase.invoke(team)
    }
}