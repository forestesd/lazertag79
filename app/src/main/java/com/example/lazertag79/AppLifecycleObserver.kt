package com.example.lazertag79

import android.util.Log
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.example.comon.game.data.WebSocketServer

class AppLifecycleObserver(
    private val webSocketServer: WebSocketServer,
    private val nsdHelper: NSDHelper
) : DefaultLifecycleObserver {

    override fun onStop(owner: LifecycleOwner) {
        webSocketServer.stop(1000, "App destroyed")
        Log.d("WS_SERVER", "Server stopped")
        nsdHelper.unRegisterService()
    }
}