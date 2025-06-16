package com.example.mainscreen.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.mainscreen.presentation.actionTopBar.ActionTopBarMain
import com.example.mainscreen.presentation.actionTopBar.ActionTopBarViewModel
import com.example.mainscreen.presentation.taggerTeams.ConnectedTaggerViewModel
import com.example.mainscreen.presentation.taggerTeams.TeamAssignmentScreen


@Composable
fun MainScreenUI(
    serverViewModel: ServerViewModel,
    connectedTaggerViewModel: ConnectedTaggerViewModel,
    actionTopBarViewModel: ActionTopBarViewModel,
    onStart: () -> Unit
) {

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        ActionTopBarMain(
            serverViewModel = serverViewModel,
            actionTopBarViewModel = actionTopBarViewModel,
            onStart = onStart
        )
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            TeamAssignmentScreen(
                serverViewMode = serverViewModel,
                connectedTaggerViewModel = connectedTaggerViewModel
            )
        }
    }

}