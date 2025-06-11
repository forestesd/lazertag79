package com.example.mainscreen.presentation.actionTopBar

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mainscreen.R
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
                .fillMaxWidth(0.4f)
                .weight(1f)
                .background(MaterialTheme.colorScheme.surface),
        ) {
        }
        Row(
            modifier = Modifier
                .fillMaxWidth(0.4f)
                .weight(1f)
                .background(MaterialTheme.colorScheme.surface),
        ) {
        }
        Box(modifier = Modifier
            .fillMaxSize()
            .weight(1f)) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.surface)
            ) {
                TeamPlayersCount(
                    count = taggers.count { it?.teamId == 2 },
                    teamID = 2
                )
                TeamName(
                    teamId = 2,
                    actionTopBarViewModel = actionTopBarViewModel,
                    modifier = Modifier.weight(1f)
                )
                StartButton(
                    Modifier
                        .padding(horizontal = 60.dp)
                        .weight(1f)
                )
                TeamName(
                    teamId = 1,
                    actionTopBarViewModel = actionTopBarViewModel,
                    modifier = Modifier.weight(1f)
                )
                TeamPlayersCount(
                    count = taggers.count { it?.teamId == 1 },
                    teamID = 1
                )
            }
        }

    }
}

@Composable
fun TeamPlayersCount(
    count: Int,
    teamID: Int,
    modifier: Modifier = Modifier

) {
    Box(
        modifier = modifier
            .height(70.dp)
            .width(160.dp)
            .clip(
                RoundedCornerShape(
                    topEnd = if (teamID == 2) 54.dp else 0.dp,
                    topStart = if (teamID == 2) 0.dp else 54.dp
                )
            )
            .background(
                Color(0xFFD9D9D9)
            ),
    ) {
        Text(
            text = "Игроков \n${count}",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier
                .align(Alignment.Center)
                .padding(
                    start = if (teamID == 2) 0.dp else 5.dp,
                    end = if (teamID == 2) 5.dp else 0.dp,
                ),
            textAlign = TextAlign.Center,
            fontSize = 26.sp
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TeamName(
    teamId: Int,
    actionTopBarViewModel: ActionTopBarViewModel,
    modifier: Modifier = Modifier
) {
    val teams by actionTopBarViewModel.teams.collectAsState()
    val isTeamNameChange = remember { mutableStateOf(false) }

    if (isTeamNameChange.value) {
        ChangeNameDialog(
            name = teams.find { it.teamId == teamId }?.teamName ?: "Команда",
            onDismis = {
                isTeamNameChange.value = !isTeamNameChange.value
            },
            onConfirm = {
                val newName = teams.find { team -> team.teamId == teamId }
                newName?.teamName = it
                actionTopBarViewModel.updateTeamName(newName)
                isTeamNameChange.value = !isTeamNameChange.value
            }
        )
    }


    Box(
        modifier = modifier
            .height(70.dp)
            .width(264.dp)
            .padding(
                start = if (teamId == 2) 74.dp else 0.dp,
                end = if (teamId == 2) 0.dp else 74.dp
            )
            .clip(RoundedCornerShape(topEnd = 16.dp, topStart = 16.dp))
            .background(
                if (teamId == 2) Color(0xFF4F378B) else Color(0xFFB3261E)
            )
            .clickable {
                isTeamNameChange.value = !isTeamNameChange.value
            }

    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "${teams.find { it.teamId == teamId }?.teamName}",
                fontSize = 28.sp,
                textAlign = TextAlign.Center,
                color = Color(0xFFFFFFFF),
                modifier = Modifier
                    .padding(horizontal = 10.dp)
                    .basicMarquee(),
                maxLines = 1,
                overflow = TextOverflow.Visible
            )
            Icon(
                painter = painterResource(R.drawable.edit),
                contentDescription = "Edit Icon",
                tint = Color(0xFFFFFFFF),
            )
        }

    }

}

@Composable
fun StartButton(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .height(75.dp)
            .width(260.dp)
            .clip(RoundedCornerShape(topEnd = 45.dp, topStart = 45.dp))
            .background(Color(0xFFB9FF93))
            .clickable {

            }
    ) {
        Text(
            text = "Старт",
            fontSize = 28.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.align(Alignment.Center)
        )
    }
}


@Composable
fun ChangeNameDialog(
    name: String,
    onDismis: () -> Unit,
    onConfirm: (String) -> Unit
) {
    var newName by remember { mutableStateOf("") }

    AlertDialog(
        confirmButton = {
            TextButton(
                onClick = {
                    onConfirm(newName)
                }
            ) {
                Text(
                    text = "ОК"
                )
            }
        },
        dismissButton = {
            TextButton(
                onClick = {
                    onDismis()
                }
            ) {
                Text(
                    text = "Отмена"
                )
            }
        },
        onDismissRequest = { onDismis() },
        title = {
            Text(name)
        },
        text = {
            OutlinedTextField(
                value = newName,
                onValueChange = {
                    newName = it
                },
                label = {
                    Text(
                        text = "Введите новое имя"
                    )
                }

            )
        }
    )

}