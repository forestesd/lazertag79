package com.example.lazertag79

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.comon.game.data.WebSocketServer
import com.example.featuregame.presentation.GameViewModel
import com.example.lazertag79.ui.theme.Lazertag79Theme
import com.example.mainscreen.presentation.taggerTeams.ConnectedTaggerViewModel
import com.example.mainscreen.presentation.ServerViewModel
import com.example.mainscreen.presentation.actionTopBar.ActionTopBarViewModel
import com.example.navigation.navGraph.AppNavigation
import com.example.setings.SettingsViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var webSocketServer: WebSocketServer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {

            Lazertag79Theme {
                val serverViewModel: ServerViewModel = hiltViewModel()
                val connectedTaggerViewModel: ConnectedTaggerViewModel = hiltViewModel()
                val actionTopBarViewModel: ActionTopBarViewModel = hiltViewModel()
                val gameViewModel: GameViewModel = hiltViewModel()
                val settingsViewModel: SettingsViewModel = hiltViewModel()

                AppNavigation(
                    serverViewModel = serverViewModel,
                    connectedTaggerViewModel = connectedTaggerViewModel,
                    actionTopBarViewModel = actionTopBarViewModel,
                    gameViewModel = gameViewModel,
                    settingsViewModel = settingsViewModel
                )
            }
        }
    }


}