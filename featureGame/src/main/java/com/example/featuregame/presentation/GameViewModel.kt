package com.example.featuregame.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.comon.Teams.domain.domain.models.TeamModel
import com.example.comon.Teams.domain.domain.useCases.TeamsUseCase
import com.example.comon.game.data.WebSocketManager
import com.example.comon.game.domain.models.Game
import com.example.comon.game.domain.use_cases.GameStartUseCase
import com.example.comon.game.domain.use_cases.GameUseCase
import com.example.comon.game.domain.use_cases.StartWebSocketSubscribeUseCase
import com.example.comon.models.TaggerInfo
import com.example.comon.server.domain.useCases.TaggersInfoUseCAse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.time.LocalTime
import javax.inject.Inject

@HiltViewModel
class GameViewModel @Inject constructor(
    teamsUseCase: TeamsUseCase,
    taggerInfoUseCase: TaggersInfoUseCAse,
    gameUseCase: GameUseCase,
    private val gameStartUseCase: GameStartUseCase,
    private val startWebSocketSubscribeUseCase: StartWebSocketSubscribeUseCase,
    private val webSocketManager: WebSocketManager
) : ViewModel() {
    val teams: StateFlow<List<TeamModel>> = teamsUseCase()
    val taggersInfo: StateFlow<List<TaggerInfo>> = taggerInfoUseCase()

    val message: StateFlow<String?> = webSocketManager.incomingMessages

    val game: StateFlow<Game> = gameUseCase()
    private val _timerBeforeStart = MutableStateFlow(game.value.timeBeforeStart)

    val timerBeforeStart: StateFlow<LocalTime> = _timerBeforeStart

    private val _gameTimer = MutableStateFlow(game.value.gameTime)
    val gameTimer: StateFlow<LocalTime> = _gameTimer

    private var job: Job? = null

    fun startCountdown() {
        job?.cancel()
        job = viewModelScope.launch {
            while (_timerBeforeStart.value > LocalTime.MIDNIGHT) {
                delay(1000L)
                _timerBeforeStart.value = _timerBeforeStart.value.minusSeconds(1)
            }
            gameStartUseCase.invoke()

            while (_gameTimer.value > LocalTime.MIDNIGHT) {
                delay(1000L)
                _gameTimer.value = _gameTimer.value.minusSeconds(1)
            }

        }
    }

    fun stopCountdown() {
        job?.cancel()
    }

    fun resetCountdown(timer: LocalTime) {

    }

    fun subscribe(){
        viewModelScope.launch {
            startWebSocketSubscribeUseCase.invoke(taggersInfo.value)
        }
    }

    fun unSubscribe(){
        webSocketManager.disconnect()
    }
}