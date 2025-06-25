package com.example.lazertag79

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.example.comon.game.data.WebSocketServer

class AppLifecycleObserver(
    private val webSocketServer: WebSocketServer,
    private val nsdHelper: NSDHelper
) : DefaultLifecycleObserver {

    override fun onDestroy(owner: LifecycleOwner) {
        webSocketServer.stop(1000, "App destroyed")
        nsdHelper.unRegisterService()
    }
}