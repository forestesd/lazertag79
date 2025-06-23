package com.example.featuregame.presentation

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import java.time.Duration

@SuppressLint("DefaultLocale")
@Composable
fun GameCountDown(
    gameViewModel: GameViewModel
) {
    val timerBeforeStart by gameViewModel.timerBeforeStart.collectAsState()
    val gameTimer by gameViewModel.gameTimer.collectAsState()

    val currentTimer = if (timerBeforeStart > Duration.ZERO) timerBeforeStart else gameTimer

    LaunchedEffect(Unit) {
        gameViewModel.startCountdown()
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Text(
            text = if (timerBeforeStart >Duration.ZERO) "Время до старта" else "Время раунда",
            fontSize = 22.sp,
            textAlign = TextAlign.Center
        )

        Text(
            text = String.format("%02d:%02d", currentTimer.toMinutes(), currentTimer.seconds.toInt() % 60),
            fontSize = 26.sp,
            textAlign = TextAlign.Center
        )
    }

}