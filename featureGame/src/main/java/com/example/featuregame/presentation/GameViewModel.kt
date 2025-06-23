package com.example.featuregame.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.comon.Teams.domain.domain.models.TeamModel
import com.example.comon.Teams.domain.domain.useCases.TeamsUseCase
import com.example.comon.game.data.WebSocketServer
import com.example.comon.game.domain.models.Game
import com.example.comon.game.domain.use_cases.GameStartUseCase
import com.example.comon.game.domain.use_cases.GameUseCase
import com.example.comon.models.TaggerInfo
import com.example.comon.models.TaggerInfoGame
import com.example.comon.models.TaggerInfoGameRes
import com.example.comon.server.domain.useCases.TaggersInfoUseCAse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.time.Duration
import javax.inject.Inject

@HiltViewModel
class GameViewModel @Inject constructor(
    teamsUseCase: TeamsUseCase,
    taggerInfoUseCase: TaggersInfoUseCAse,
    gameUseCase: GameUseCase,
    private val gameStartUseCase: GameStartUseCase,
    private val webSocketServer: WebSocketServer
) : ViewModel() {

    val teams: StateFlow<List<TeamModel>> = teamsUseCase()

    val taggersInfo: StateFlow<List<TaggerInfo>> = taggerInfoUseCase()

    val taggerGame: StateFlow<List<TaggerInfoGame>> = webSocketServer.incomingMessages

    val game: StateFlow<Game> = gameUseCase()

    private val _timerBeforeStart = MutableStateFlow(game.value.timeBeforeStart)
    val timerBeforeStart: StateFlow<Duration> = _timerBeforeStart

    private val _gameTimer = MutableStateFlow(game.value.gameTime)
    val gameTimer: StateFlow<Duration> = _gameTimer

    private var job: Job? = null

    init {
        viewModelScope.launch {
            game.collect { gameState ->
                _timerBeforeStart.value = gameState.timeBeforeStart
                _gameTimer.value = gameState.gameTime
            }
        }
    }

    fun startCountdown() {
        job?.cancel()
        job = viewModelScope.launch {
            gameStartUseCase.invoke()
            while (_timerBeforeStart.value > Duration.ZERO) {
                delay(1000L)
                _timerBeforeStart.value = _timerBeforeStart.value.minusSeconds(1)
            }


            while (_gameTimer.value > Duration.ZERO) {
                delay(1000L)
                _gameTimer.value = _gameTimer.value.minusSeconds(1)
            }

        }
    }

    fun stopCountdown() {
        job?.cancel()
    }


}