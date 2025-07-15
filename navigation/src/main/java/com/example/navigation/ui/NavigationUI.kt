package com.example.navigation.ui

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@Composable
fun NavigationUI(
    onClick: (Int) -> Unit,
    content: @Composable (PaddingValues) -> Unit
) {
    Scaffold(
        containerColor = Color.Black,
        bottomBar = {
            BottomAppBar(
                onClick = onClick
            )
        }
    ) {
        content(it)
    }
}