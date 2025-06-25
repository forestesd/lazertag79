package com.example.lazertag79

import android.content.Context
import android.net.nsd.NsdManager
import android.net.nsd.NsdServiceInfo
import android.util.Log
import javax.inject.Inject

class NSDHelper @Inject constructor(
    private val context: Context
) {

    private var nsdManager: NsdManager? = null
    private var registrationListener: NsdManager.RegistrationListener? = null

    fun registerService() {

        nsdManager = context.getSystemService(Context.NSD_SERVICE) as NsdManager

        val serviceInfo = NsdServiceInfo().apply {
            serviceName = "ESPWebSocket"
            serviceType = "_ws._tcp."
            port = 8080
        }

        registrationListener = object : NsdManager.RegistrationListener {
            override fun onRegistrationFailed(serviceInfo: NsdServiceInfo?, errorCode: Int) {
                Log.e("NsdHelper", "Registration failed $errorCode")
            }

            override fun onServiceRegistered(serviceInfo: NsdServiceInfo?) {
                Log.i("NsdHelper", "Service registered ${serviceInfo?.serviceName}")
            }

            override fun onUnregistrationFailed(serviceInfo: NsdServiceInfo?, errorCode: Int) {
                Log.e("NsdHelper", "Unregistered failed $errorCode")
            }

            override fun onServiceUnregistered(serviceInfo: NsdServiceInfo?) {
                Log.i("NsdHelper", "Service unregistered")
            }
        }

        nsdManager?.registerService(
            serviceInfo,
            NsdManager.PROTOCOL_DNS_SD,
            registrationListener
        )

    }

    fun unRegisterService() {

        registrationListener?.let {
            nsdManager?.unregisterService(it)
        }

    }

}