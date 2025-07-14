package com.example.mainscreen.presentation.taggerTeams

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.DrawerValue
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.example.comon.models.TaggerInfo
import com.example.mainscreen.presentation.ServerViewModel
import com.example.mainscreen.presentation.actionTopBar.ActionTopBarViewModel
import com.mohamedrejeb.compose.dnd.rememberDragAndDropState

@Composable
fun TeamAssignmentScreen(
    serverViewMode: ServerViewModel,
    connectedTaggerViewModel: ConnectedTaggerViewModel,
    actionTopBarViewModel: ActionTopBarViewModel,
    ) {
    val dndState = rememberDragAndDropState<TaggerInfo?>()
    val drawState by connectedTaggerViewModel.drawerState.collectAsState()

    Box(modifier = Modifier.fillMaxSize()) {
        Row(
            modifier = Modifier.fillMaxSize()
        ) {

            val animatedWeight by animateFloatAsState(
                targetValue = if (drawState == DrawerValue.Open) 0.6f else 0.00001f,
                animationSpec = tween(durationMillis = 200)
            )
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(animatedWeight)
            ) {
                ConnectedTaggers(
                    serverViewMode = serverViewMode,
                    dndState = dndState,
                    connectedTaggerViewModel = connectedTaggerViewModel
                )
            }

            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f)
            ) {
                BlueTeamList(serverViewMode, dndState, actionTopBarViewModel)
            }

            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f)
            ) {
                RedTeamList(serverViewMode, dndState, actionTopBarViewModel )
            }
        }
    }
}