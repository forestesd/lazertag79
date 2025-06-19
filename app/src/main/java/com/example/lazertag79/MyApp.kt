package com.example.lazertag79

import android.app.Application
import android.content.Context
import android.net.wifi.WifiManager
import android.text.format.Formatter
import android.util.Log
import com.example.comon.game.data.WebSocketServer
import com.example.comon.server.domain.useCases.ConnectTaggerUseCase
import dagger.hilt.android.HiltAndroidApp
import java.io.File
import javax.inject.Inject

@HiltAndroidApp
class MyApp : Application() {
    @Inject
    lateinit var connectTaggerUseCase: ConnectTaggerUseCase
    @Inject
    lateinit var webSocketServer: WebSocketServer

    override fun onCreate() {
        super.onCreate()
        startServer()

        copyRawConfigToFileIfNeeded(this)
    }

    override fun onTerminate() {
        super.onTerminate()
        webSocketServer.stop()
        Log.d("WS_SERVER", "Server stopped in onTerminate()")
    }

    private fun startServer(){
        val port = 8080
        webSocketServer.start()
        Log.d("WS_SERVER", "Server started on port: $port")
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