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
import com.example.mainscreen.MainScreenUI
import com.example.mainscreen.ServerViewModel
import com.example.navigation.UI.NavigationUI

@Composable
fun AppNavigation(
    serverViewModel: ServerViewModel
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
            navController.navigate("finance")
        },
        onNewsFeedClick = {
            selectedItem = 2
            navController.navigate("notes")
        }
    ) { paddingValues ->

        NavHost(
            navController = navController,
            startDestination = "main",
            modifier = Modifier.padding(paddingValues)

        ) {
            composable("main") {
                MainScreenUI(serverViewModel)
            }
        }
    }
}