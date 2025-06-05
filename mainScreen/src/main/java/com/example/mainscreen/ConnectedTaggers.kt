package com.example.mainscreen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.comon.models.TaggerInfo
import com.mohamedrejeb.compose.dnd.DragAndDropContainer
import com.mohamedrejeb.compose.dnd.DragAndDropState
import com.mohamedrejeb.compose.dnd.drag.DraggableItem
import com.mohamedrejeb.compose.dnd.drop.dropTarget
import com.mohamedrejeb.compose.dnd.rememberDragAndDropState

@Composable
fun TeamAssignmentScreen(serverViewMode: ServerViewModel) {
    val dndState = rememberDragAndDropState<TaggerInfo?>()
    Row(
        modifier = Modifier.fillMaxSize()
    ) {
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .weight(0.7f)
        ) {
            ConnectedTaggers(
                serverViewMode = serverViewMode,
                dndState = dndState
            )
        }

        Box(
            modifier = Modifier
                .fillMaxHeight()
                .weight(1f)
        ) {
            BlueTeamList(
                serverViewMode = serverViewMode,
                dndState = dndState
            )
        }

        Box(
            modifier = Modifier
                .fillMaxHeight()
                .weight(1f)
        ) {
            RedTeamList(
                serverViewMode = serverViewMode,
                dndState = dndState
            )
        }
    }
}

@Composable
fun ConnectedTaggers(
    serverViewMode: ServerViewModel,
    dndState: DragAndDropState<TaggerInfo?>
) {
    val taggers by serverViewMode.taggerData.collectAsState()
    val onDragTaggerId by serverViewMode.onDragTaggerId.collectAsState()

    DragAndDropContainer(
        state = dndState
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth()
                .background(
                    color = Color(0xFFF0EAFF),
                ),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            items(
                items = taggers
                    .filterNotNull()
                    .filter { it.teamId == 0 }
                    .filter { it.taggerId != onDragTaggerId },
                key = { item -> item.taggerId }) { item ->
                AnimatedVisibility(true) {
                    DraggableItem(
                        state = dndState,
                        key = item.taggerId,
                        data = item,
                    ) {
                        ConnectedTaggerCard(item)
                    }
                }
            }
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .dropTarget(
                    state = dndState,
                    key = "NONETeamDNDArea",
                    onDrop = { droppedData ->
                        droppedData.let {
                            serverViewMode.changeTeam(it.data, 0)
                            serverViewMode.onDrag(null)
                        }
                    },
                    onDragEnter = {
                        serverViewMode.onDrag(it.data!!.taggerId)
                    },
                    onDragExit = {
                        serverViewMode.onDrag(null)
                    }
                )
        )
    }

}

@Composable
fun ConnectedTaggerCard(tagger: TaggerInfo?) {
    Card(
        modifier = Modifier
            .height(181.dp)
            .width(111.dp)

    ) {
        Text(
            text = tagger?.taggerId.toString()
        )
    }
}

@Composable
fun BlueTeamList(
    serverViewMode: ServerViewModel,
    dndState: DragAndDropState<TaggerInfo?>
) {
    val taggers by serverViewMode.taggerData.collectAsState()
    val onDragTaggerId by serverViewMode.onDragTaggerId.collectAsState()

    DragAndDropContainer(
        state = dndState
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth()
                .background(
                    brush = Brush.verticalGradient(
                        listOf(
                            Color(0xFFAC8BFF),
                            Color(0xFF4900FF)
                        )
                    ),
                )
        ) {
            items(items = taggers
                .filterNotNull()
                .filter { it.teamId == 2 }
                .filter { it.taggerId != onDragTaggerId },
                key = { item -> item.taggerId }) { tagger ->
                AnimatedVisibility(true) {
                    DraggableItem(
                        state = dndState,
                        key = tagger.taggerId,
                        data = tagger
                    ) {
                        ConnectedTaggerCard(tagger)
                    }
                }

            }
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .dropTarget(
                    state = dndState,
                    key = "BlueTeamDNDArea",
                    onDrop = { droppedData ->
                        droppedData.let {
                            serverViewMode.changeTeam(it.data, 2)
                            serverViewMode.onDrag(null)
                        }
                    },
                    onDragEnter = {
                        serverViewMode.onDrag(it.data!!.taggerId)
                    },
                    onDragExit = {
                        serverViewMode.onDrag(null)
                    }
                )
        )
    }
}

@Composable
fun RedTeamList(
    serverViewMode: ServerViewModel,
    dndState: DragAndDropState<TaggerInfo?>

) {
    val taggers by serverViewMode.taggerData.collectAsState()
    val onDragTaggerId by serverViewMode.onDragTaggerId.collectAsState()

    DragAndDropContainer(
        state = dndState
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth()
                .background(
                    brush = Brush.verticalGradient(
                        listOf(
                            Color(0xFFB3261E),
                            Color(0xFFFF0004)
                        )
                    ),
                )
        ) {
            items(
                items = taggers
                    .filterNotNull()
                    .filter { it.teamId == 1 }
                    .filter { it.taggerId != onDragTaggerId },
                key = { item ->
                    item.taggerId
                }) { tagger ->
                AnimatedVisibility(true) {
                    DraggableItem(
                        state = dndState,
                        key = tagger.taggerId,
                        data = tagger
                    ) {
                        ConnectedTaggerCard(tagger)
                    }
                }

            }
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .dropTarget(
                    state = dndState,
                    key = "RedTeamDNDArea",
                    onDrop = { droppedData ->
                        droppedData.let {
                            serverViewMode.changeTeam(it.data, 1)
                            serverViewMode.onDrag(null)
                        }
                    },
                    onDragEnter = {
                        serverViewMode.onDrag(it.data!!.taggerId)
                    },
                    onDragExit = {
                        serverViewMode.onDrag(null)
                    }
                )
        )
    }
}