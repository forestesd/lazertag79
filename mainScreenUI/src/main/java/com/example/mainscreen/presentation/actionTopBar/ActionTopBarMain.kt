package com.example.mainscreen.presentation.actionTopBar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.mainscreen.presentation.ServerViewModel

@Composable
fun ActionTopBarMain(
    serverViewModel: ServerViewModel,
    actionTopBarViewModel: ActionTopBarViewModel
) {
    val taggers by serverViewModel.taggerData.collectAsState()
    val teams by actionTopBarViewModel.teams.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .weight(1f)
                .background(MaterialTheme.colorScheme.surface),
        ) {
        }
        Row(
            modifier = Modifier
                .fillMaxSize()
                .weight(1f)
                .background(MaterialTheme.colorScheme.surface),
        ) {
        }
        Row(
            modifier = Modifier
                .fillMaxSize()
                .weight(1f)
                .background(MaterialTheme.colorScheme.surface)
        ) {
            TeamPlayersCount(taggers.count { it?.teamId == 2 })

        }
    }
}

@Composable
fun TeamPlayersCount(
    count: Int
) {
    Box(
        modifier = Modifier
            .height(70.dp)
            .width(160.dp)
            .clip(RoundedCornerShape(topEnd = 54.dp))
            .background(
                Color(0xFFD9D9D9)
            ),
    ) {
        Text(
            text = "Игроков \n${count}",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.align(Alignment.Center),
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun TeamName(){

}