package com.example.lazertag79

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.lazertag79.ui.theme.Lazertag79Theme
import com.example.mainscreen.ServerViewModel
import com.example.navigation.navGraph.AppNavigation
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private var serverJob: Job? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {

            Lazertag79Theme {
                val serverViewModel: ServerViewModel = hiltViewModel()
                AppNavigation(serverViewModel)
            }
        }
    }

}