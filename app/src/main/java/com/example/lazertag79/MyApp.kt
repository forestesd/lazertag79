package com.example.lazertag79

import android.app.Application
import android.content.Context
import android.net.wifi.WifiManager
import android.text.format.Formatter
import android.util.Log
import com.example.comon.server.domain.useCases.ConnectTaggerUseCase
import com.example.serverv3.startServer
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

@HiltAndroidApp
class MyApp : Application() {
    @Inject
    lateinit var connectTaggerUseCase: ConnectTaggerUseCase



    override fun onCreate() {
        super.onCreate()

        val ip = getLocalIpAddress()
        CoroutineScope(Dispatchers.IO).launch {
            startServer(ip, 8080, connectTaggerUseCase = connectTaggerUseCase)
            Log.d("Server", "Server started on http://$ip:8080")
        }
        copyRawConfigToFileIfNeeded(this)
    }

    private fun copyRawConfigToFileIfNeeded(context: Context) {
        val file = File(context.filesDir, "config.json")
        if (!file.exists()) {
            context.resources.openRawResource(R.raw.config).use { input ->
                file.outputStream().use { output ->
                    input.copyTo(output)
                }
            }
        }
    }

    private fun getLocalIpAddress(): String {
        val wifiManager = applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager
        val ip = wifiManager.connectionInfo.ipAddress
        return Formatter.formatIpAddress(ip)
    }
}