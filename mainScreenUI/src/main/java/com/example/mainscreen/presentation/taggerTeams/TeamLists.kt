package com.example.mainscreen.presentation.taggerTeams

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import com.example.comon.models.TaggerInfo
import com.example.mainscreen.presentation.ServerViewModel
import com.example.mainscreen.presentation.actionTopBar.ActionTopBarViewModel
import com.mohamedrejeb.compose.dnd.DragAndDropContainer
import com.mohamedrejeb.compose.dnd.DragAndDropState
import com.mohamedrejeb.compose.dnd.drag.DraggableItem
import com.mohamedrejeb.compose.dnd.drop.dropTarget

@Composable
fun RedTeamList(
    serverViewMode: ServerViewModel,
    dndState: DragAndDropState<TaggerInfo?>,
    actionTopBarViewModel: ActionTopBarViewModel,
    ) {
    val game by actionTopBarViewModel.game.collectAsState()
    val taggers by serverViewMode.taggerData.collectAsState()
    val onDragTaggerId by serverViewMode.onDragTaggerId.collectAsState()

    DragAndDropContainer(
        state = dndState,
        enabled = !game.isGameStart
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
                        TeamPlayerCard(tagger)
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
                            it.data?.let { it1 -> serverViewMode.changeTeam(it1, 1) }
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
fun BlueTeamList(
    serverViewMode: ServerViewModel,
    dndState: DragAndDropState<TaggerInfo?>,
    actionTopBarViewModel: ActionTopBarViewModel,
) {
    val game by actionTopBarViewModel.game.collectAsState()
    val taggers by serverViewMode.taggerData.collectAsState()
    val onDragTaggerId by serverViewMode.onDragTaggerId.collectAsState()

    DragAndDropContainer(
        state = dndState,
        enabled = !game.isGameStart
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
                .filter { it.teamId == 2 }
                .filter { it.taggerId != onDragTaggerId },
                key = { item -> item.taggerId }) { tagger ->
                AnimatedVisibility(true) {
                    DraggableItem(
                        state = dndState,
                        key = tagger.taggerId,
                        data = tagger
                    ) {
                        TeamPlayerCard(tagger)
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
                            it.data?.let { it1 -> serverViewMode.changeTeam(it1, 2) }
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


