package com.example.mainscreen.presentation.taggerTeams

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import com.example.comon.models.TaggerInfo
import com.example.mainscreen.presentation.ServerViewModel
import com.mohamedrejeb.compose.dnd.DragAndDropContainer
import com.mohamedrejeb.compose.dnd.DragAndDropState
import com.mohamedrejeb.compose.dnd.drag.DraggableItem
import com.mohamedrejeb.compose.dnd.drop.dropTarget
import kotlinx.coroutines.flow.distinctUntilChanged

@Composable
fun ConnectedTaggers(
    serverViewMode: ServerViewModel,
    dndState: DragAndDropState<TaggerInfo?>,
    connectedTaggerViewModel: ConnectedTaggerViewModel
) {
    val taggers by serverViewMode.taggerData.collectAsState()
    val onDragTaggerId by serverViewMode.onDragTaggerId.collectAsState()

    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val drawerValue by connectedTaggerViewModel.drawerState.collectAsState()

    LaunchedEffect(taggers) {
        if (taggers.count{it?.teamId == 0} != 0) {
            connectedTaggerViewModel.openDrawer()
        } else {
            connectedTaggerViewModel.closeDrawer()
        }
    }

    LaunchedEffect(drawerValue) {
        when (drawerValue) {
            DrawerValue.Open -> drawerState.open()
            DrawerValue.Closed -> drawerState.close()
        }
    }

    LaunchedEffect(drawerState) {
        snapshotFlow { drawerState.currentValue }
            .distinctUntilChanged()
            .collect { value ->
                when (value) {
                    DrawerValue.Open -> connectedTaggerViewModel.openDrawer()
                    DrawerValue.Closed -> connectedTaggerViewModel.closeDrawer()
                }
            }
    }

    Row(modifier = Modifier.fillMaxSize()) {

        Box(modifier = Modifier.weight(1f)) {
            ModalNavigationDrawer(
                drawerState = drawerState,
                drawerContent = {
                    DragAndDropContainer(state = dndState) {

                        LazyColumn(
                            modifier = Modifier
                                .fillMaxHeight()
                                .fillMaxWidth()
                                .background(Color(0xFFF0EAFF)),
                            verticalArrangement = Arrangement.Top,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            items(
                                items = taggers
                                    .filterNotNull()
                                    .filter { it.teamId == 0 }
                                    .filter { it.taggerId != onDragTaggerId },
                                key = { item -> item.taggerId }
                            ) { item ->
                                this@Row.AnimatedVisibility(true) {
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
                                        serverViewMode.changeTeam(droppedData.data, 0)
                                        serverViewMode.onDrag(null)
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
                },
                content = {}
            )
        }
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .width(24.dp)
                .background(Color(0xFFC7BBE3))
                .pointerInput(Unit) {
                    detectHorizontalDragGestures { _, dragAmount ->
                        if (dragAmount > 10) {
                            connectedTaggerViewModel.openDrawer()
                        } else if (dragAmount < -10) {
                            connectedTaggerViewModel.closeDrawer()
                        }
                    }
                }
                .clickable {
                    if (drawerState.isOpen) {
                        connectedTaggerViewModel.closeDrawer()
                    } else {
                        connectedTaggerViewModel.openDrawer()
                    }
                },
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = if (drawerState.isOpen) Icons.AutoMirrored.Filled.KeyboardArrowLeft else Icons.AutoMirrored.Filled.KeyboardArrowRight,
                contentDescription = "Toggle Drawer",
                tint = Color.White
            )
        }

    }
}




