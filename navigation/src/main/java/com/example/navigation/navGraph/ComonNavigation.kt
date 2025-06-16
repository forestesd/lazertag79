package com.example.navigation.navGraph

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.featuregame.MainScreen
import com.example.mainscreen.presentation.taggerTeams.ConnectedTaggerViewModel
import com.example.mainscreen.presentation.MainScreenUI
import com.example.mainscreen.presentation.ServerViewModel
import com.example.mainscreen.presentation.actionTopBar.ActionTopBarViewModel
import com.example.navigation.UI.NavigationUI

@Composable
fun AppNavigation(
    serverViewModel: ServerViewModel,
    connectedTaggerViewModel: ConnectedTaggerViewModel,
    actionTopBarViewModel: ActionTopBarViewModel
) {


    val navController = rememberNavController()
    var selectedItem by remember { mutableIntStateOf(0) }

    NavigationUI(
        selectedItem = selectedItem,
        onMainClick = {
            selectedItem = 0
            navController.navigate("main")
        },
        onFinanceClick = {
            selectedItem = 1
            navController.navigate("")
        },
        onNewsFeedClick = {
            selectedItem = 2
            navController.navigate("")
        }
    ) { paddingValues ->

        NavHost(
            navController = navController,
            startDestination = "main",
            modifier = Modifier.padding(paddingValues)

        ) {
            composable("main") {
                MainScreenUI(
                    serverViewModel = serverViewModel,
                    connectedTaggerViewModel = connectedTaggerViewModel,
                    actionTopBarViewModel = actionTopBarViewModel,
                    onStart = {navController.navigate("game")}
                )
            }
            composable("game") {
                MainScreen()
            }
        }
    }
}