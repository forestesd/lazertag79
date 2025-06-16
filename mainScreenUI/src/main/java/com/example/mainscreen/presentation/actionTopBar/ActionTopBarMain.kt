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
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Switch
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
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.comon.models.TaggerInfo
import com.example.mainscreen.R
import com.example.mainscreen.presentation.ServerViewModel

@Composable
fun ActionTopBarMain(
    serverViewModel: ServerViewModel,
    actionTopBarViewModel: ActionTopBarViewModel,
    onStart: () -> Unit
) {
    val taggers by serverViewModel.taggerData.collectAsState()
    val teams by actionTopBarViewModel.teams.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .background(MaterialTheme.colorScheme.surface),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {

        Row(
            modifier = Modifier
                .fillMaxSize()
                .weight(1f),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f),
                verticalArrangement = Arrangement.Top
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                ) {
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                ) {
                }
            }
            GameTime(
                modifier = Modifier
                    .weight(0.6f)
                    .padding(bottom = 10.dp),
                actionTopBarViewModel = actionTopBarViewModel
            )
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f),
                verticalArrangement = Arrangement.Top
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                ) {
                }
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxSize(),
                    ) {

                        TeamWins(
                            modifier = Modifier
                                .weight(0.8f)
                                .fillMaxHeight()
                                .padding(vertical = 10.dp),
                            wins = teams.find { it.teamId == 2 }?.wins ?: 0
                        )
                        Spacer(
                            modifier = Modifier
                                .fillMaxHeight()
                                .width(10.dp)
                        )
                        FriendlyFire(
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxHeight()
                                .padding(vertical = 10.dp),
                            actionTopBarViewModel = actionTopBarViewModel,
                            taggers = taggers,
                            serverViewModel = serverViewModel
                        )


                    }
                }

            }
        }

        BottomRow(
            modifier = Modifier.weight(0.5f),
            actionTopBarViewModel = actionTopBarViewModel,
            taggers = taggers,
            onStart = onStart
        )
    }
}

@Composable
fun TeamWins(
    modifier: Modifier = Modifier,
    wins: Int
) {
    Row(
        modifier = modifier
            .clip(
                RoundedCornerShape(
                    topStart = 55.dp,
                    topEnd = 15.dp,
                    bottomStart = 15.dp,
                    bottomEnd = 55.dp
                )
            )
            .background(Color(0xFFFBFFBD)),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Побед: $wins",
            fontSize = 18.sp,
            modifier = Modifier.padding(horizontal = 16.dp)
        )
    }
}

@Composable
fun FriendlyFire(
    modifier: Modifier = Modifier,
    actionTopBarViewModel: ActionTopBarViewModel,
    taggers: List<TaggerInfo>,
    serverViewModel: ServerViewModel,
) {
    val game by actionTopBarViewModel.game.collectAsState()

    Row(
        modifier = modifier
            .clip(RoundedCornerShape(topStart = 55.dp, bottomStart = 55.dp))
            .background(Color(0xFFEADDFF)),
        horizontalArrangement = Arrangement.End,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Огонь по своим",
            fontSize = 18.sp,
            modifier = Modifier.padding(horizontal = 16.dp)
        )
        Switch(
            checked = game.friendlyFireMode,
            onCheckedChange = {
                actionTopBarViewModel.changeFriendlyFireMode(
                    friendlyFireMode = !game.friendlyFireMode,
                    taggers = taggers
                )
                serverViewModel.changeTaggerInfo(taggers.map { it.copy(friendlyFire = !game.friendlyFireMode) })
            },
            modifier = Modifier.padding(end = 10.dp)
        )
    }
}

@Composable
fun GameTime(
    modifier: Modifier = Modifier,
    actionTopBarViewModel: ActionTopBarViewModel

) {
    val game by actionTopBarViewModel.game.collectAsState()

    var isTimeBeforeGameChange by remember { mutableStateOf(false) }
    var isGameTimeChange by remember { mutableStateOf(false) }

    if (isTimeBeforeGameChange) {
        MinutesSecondsPickerDialog(
            onDismiss = {
                isTimeBeforeGameChange = !isTimeBeforeGameChange
            },
            onConfirm = { minutes, seconds ->
                actionTopBarViewModel.changeTimeBeforeStart(
                    minutes = minutes,
                    seconds = seconds
                )
                isTimeBeforeGameChange = !isTimeBeforeGameChange
            },
            initialMinutes = game.timeBeforeStart.minute,
            initialSeconds = game.timeBeforeStart.second,
        )
    }

    if (isGameTimeChange) {
        MinutesSecondsPickerDialog(
            onDismiss = {
                isGameTimeChange = !isGameTimeChange
            },
            onConfirm = { minutes, seconds ->
                actionTopBarViewModel.changeGameTime(
                    minutes = minutes,
                    seconds = seconds
                )
                isGameTimeChange = !isGameTimeChange
            },
            initialMinutes = game.gameTime.minute,
            initialSeconds = game.gameTime.second,
        )
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .clip(RoundedCornerShape(bottomEnd = 55.dp, bottomStart = 55.dp))
            .background(Color(0xFFEADDFF))
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 10.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TimeObj(
                modifier = Modifier.weight(1f),
                text = "Время игры",
                time = "${game.gameTime.minute}:${if (game.gameTime.second > 9) "" else 0}${game.gameTime.second}",
                timePaddingValues = 55.dp,
                textFontSize = 26,
                timeFontSize = 22,
                onClick = {
                    isGameTimeChange = !isGameTimeChange
                }
            )

            TimeObj(
                modifier = Modifier.weight(1f),
                text = "Время до старта игры",
                time = "${game.timeBeforeStart.minute}:${game.timeBeforeStart.second}",
                timePaddingValues = 65.dp,
                textFontSize = 18,
                timeFontSize = 14,
                onClick = {
                    isTimeBeforeGameChange = !isTimeBeforeGameChange
                }
            )
        }
    }

}

@Composable
fun TimeObj(
    modifier: Modifier = Modifier,
    text: String,
    textFontSize: Int,
    time: String,
    timeFontSize: Int,
    timePaddingValues: Dp,
    onClick: () -> Unit
) {

    Column(
        modifier = modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = text,
            fontSize = textFontSize.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.weight(1f)
        )
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = timePaddingValues)
                .weight(1f)
                .clip(RoundedCornerShape(70.dp))
                .background(Color(0xFFD0BCFF))
                .clickable {
                    onClick()
                },
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = time,
                textAlign = TextAlign.Center,
                fontSize = timeFontSize.sp
            )
            Icon(
                painter = painterResource(R.drawable.edit),
                contentDescription = "Game Time Icon",
                modifier = Modifier.size(28.dp)
            )
        }
    }
}

@Composable
fun BottomRow(
    modifier: Modifier = Modifier,
    actionTopBarViewModel: ActionTopBarViewModel,
    taggers: List<TaggerInfo>,
    onStart: () -> Unit
) {

    Box(
        modifier = modifier
            .fillMaxSize()
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.surface)
        ) {
            TeamPlayersCount(
                count = taggers.count { it.teamId == 2 },
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
                    .weight(1f),
                onStart = onStart
            )
            TeamName(
                teamId = 1,
                actionTopBarViewModel = actionTopBarViewModel,
                modifier = Modifier.weight(1f)
            )
            TeamPlayersCount(
                count = taggers.count { it.teamId == 1 },
                teamID = 1
            )
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

    var newName by remember { mutableStateOf("") }

    if (isTeamNameChange.value) {
        ChangeDialog(
            name = teams.find { it.teamId == teamId }?.teamName ?: "Команда",
            onDismiss = {
                isTeamNameChange.value = !isTeamNameChange.value
            },
            onConfirm = {
                val newTeamName = teams.find { team -> team.teamId == teamId }
                newTeamName?.teamName = newName
                actionTopBarViewModel.updateTeamName(newTeamName)
                isTeamNameChange.value = !isTeamNameChange.value
            },
            content = {
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
                    .basicMarquee(
                        iterations = Int.MAX_VALUE
                    ),
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
    modifier: Modifier = Modifier,
    onStart: () -> Unit
) {
    Box(
        modifier = modifier
            .height(75.dp)
            .width(260.dp)
            .clip(RoundedCornerShape(topEnd = 45.dp, topStart = 45.dp))
            .background(Color(0xFFB9FF93))
            .clickable {
                onStart()
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
fun ChangeDialog(
    name: String,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
    content: @Composable () -> Unit
) {

    AlertDialog(
        confirmButton = {
            TextButton(
                onClick = {
                    onConfirm()
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
                    onDismiss()
                }
            ) {
                Text(
                    text = "Отмена"
                )
            }
        },
        onDismissRequest = { onDismiss() },
        title = {
            Text(name)
        },
        text = {
            content()
        }
    )

}