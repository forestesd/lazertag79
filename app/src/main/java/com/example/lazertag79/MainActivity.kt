package com.example.lazertag79

import android.net.wifi.WifiManager
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.LazerTag.startServer
import com.example.lazertag79.ui.theme.Lazertag79Theme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.net.InetAddress

class MainActivity : ComponentActivity() {

    private var serverJob: Job? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        startLocalServer()

        setContent {
            Lazertag79Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }

    private fun startLocalServer() {
        serverJob = CoroutineScope(Dispatchers.IO).launch {
            val ip = getLocalIpAddress()
            startServer(host = ip, port = 8080)
            Log.d("Server", "Server started on http://$ip:8080")
        }
    }

    private fun getLocalIpAddress(): String {
        val wifiManager = applicationContext.getSystemService(WIFI_SERVICE) as WifiManager
        val ip = wifiManager.connectionInfo.ipAddress
        return InetAddress.getByAddress(
            byteArrayOf(
                (ip and 0xff).toByte(),
                (ip shr 8 and 0xff).toByte(),
                (ip shr 16 and 0xff).toByte(),
                (ip shr 24 and 0xff).toByte()
            )
        ).hostAddress ?: "0.0.0.0"
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Lazertag79Theme {
        Greeting("Android")
    }
}