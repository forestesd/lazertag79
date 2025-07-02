package com.example.lazertag79

import android.app.Application
import android.content.Context
import android.net.wifi.WifiManager
import android.text.format.Formatter
import android.util.Log
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ProcessLifecycleOwner
import com.example.comon.game.data.WebSocketServer
import com.example.comon.server.domain.useCases.ConnectTaggerUseCase
import dagger.hilt.android.HiltAndroidApp
import java.io.File
import javax.inject.Inject

@HiltAndroidApp
class MyApp : Application(){
    @Inject
    lateinit var connectTaggerUseCase: ConnectTaggerUseCase
    @Inject
    lateinit var webSocketServer: WebSocketServer
    @Inject
    lateinit var nsdHelper: NSDHelper

    override fun onCreate() {
        super.onCreate()

        ProcessLifecycleOwner.get().lifecycle.addObserver(AppLifecycleObserver(webSocketServer, nsdHelper))

        startServer()
        nsdHelper.registerService()
        copyRawConfigToFileIfNeeded(this)
    }


    private fun startServer(){
        webSocketServer.start()
        Log.d("WS_SERVER", "Server started on wss://${getLocalIpAddress()}:8080")
    }

    private fun copyRawConfigToFileIfNeeded(context: Context) {
        val prefs = context.getSharedPreferences("app_prefs", MODE_PRIVATE)
        val wasCopied = prefs.getBoolean("config_copied", false)

        if (!wasCopied) {
            val file = File(context.filesDir, "config.json")
            context.resources.openRawResource(R.raw.config).use { input ->
                file.outputStream().use { output ->
                    input.copyTo(output)
                }
            }
            prefs.edit().putBoolean("config_copied", true).apply()
            Log.d("ConfigCopy", "Initial config copied.")
        } else {
            Log.d("ConfigCopy", "Initial config already copied before.")
        }
    }


    private fun getLocalIpAddress(): String {
        val wifiManager = applicationContext.getSystemService(WIFI_SERVICE) as WifiManager
        val ip = wifiManager.connectionInfo.ipAddress
        return Formatter.formatIpAddress(ip)
    }
}