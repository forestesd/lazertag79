package com.example.mainscreen.presentation.actionTopBar

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.comon.game.domain.models.Game
import com.example.comon.game.domain.use_cases.ChangeFriendlyFireModeUseCase
import com.example.comon.game.domain.use_cases.ChangeGameTimeUseCase
import com.example.comon.game.domain.use_cases.ChangeTimeBeforeStartUseCase
import com.example.comon.game.domain.use_cases.GameUseCase
import com.example.comon.models.TaggerInfo
import com.example.comon.Teams.domain.domain.models.TeamModel
import com.example.comon.Teams.domain.domain.useCases.ChangeTeamNameUseCase
import com.example.comon.Teams.domain.domain.useCases.TeamsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.time.Duration
import java.time.LocalTime
import javax.inject.Inject

@HiltViewModel
class ActionTopBarViewModel @Inject constructor(
    private val changeTeamNameUseCase: ChangeTeamNameUseCase,
    teamsUseCase: TeamsUseCase,
    gameUseCase: GameUseCase,
    private val changeGameTimeUseCase: ChangeGameTimeUseCase,
    private val changeTimeBeforeStartUseCase: ChangeTimeBeforeStartUseCase,
    private val changeFriendlyFireModeUseCase: ChangeFriendlyFireModeUseCase
) : ViewModel() {
    val teams: StateFlow<List<TeamModel>> = teamsUseCase.invoke()
    val game: StateFlow<Game> = gameUseCase.invoke()

    fun updateTeamName(team: TeamModel?) {
        changeTeamNameUseCase.invoke(team)
    }

    fun changeGameTime(minutes: Int, seconds: Int) {
        val duration = Duration.ofMinutes(minutes.toLong()).plusSeconds(seconds.toLong())

        viewModelScope.launch {
            changeGameTimeUseCase.invoke(duration)

        }

    }

    fun changeTimeBeforeStart(minutes: Int, seconds: Int) {
        val duration = Duration.ofMinutes(minutes.toLong()).plusSeconds(seconds.toLong())

        viewModelScope.launch {
            changeTimeBeforeStartUseCase.invoke(duration)
        }
    }

    fun changeFriendlyFireMode(friendlyFireMode: Boolean, taggers: List<TaggerInfo>) {
        viewModelScope.launch {
            changeFriendlyFireModeUseCase.invoke(
                friendlyFireMode = friendlyFireMode,
                taggers = taggers
            )
        }
    }

}