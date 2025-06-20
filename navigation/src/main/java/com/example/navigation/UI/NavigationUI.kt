package com.example.navigation.UI

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@Composable
fun NavigationUI(
    selectedItem: Int,
    onMainClick: () -> Unit,
    onFinanceClick: () -> Unit,
    onNewsFeedClick: () -> Unit,
    onGameClick: () -> Unit,
    content: @Composable (PaddingValues) -> Unit
) {
    Scaffold (
        containerColor = Color.White,
        bottomBar = {
            BottomNavigationBar(
                selectedItem,
                onMainClick,
                onFinanceClick,
                onNewsFeedClick,
                onGameClick
            )
        }
    ){
        content(it)
    }
}