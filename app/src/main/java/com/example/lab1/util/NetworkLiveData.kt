package com.example.lab1.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import androidx.lifecycle.LiveData

class NetworkLiveData(context: Context) : LiveData<Boolean>() {
    private val cm = context.getSystemService(ConnectivityManager::class.java)
    private val callback = object : ConnectivityManager.NetworkCallback() {
        override fun onAvailable(network: Network) { postValue(true) }
        override fun onLost(network: Network) { postValue(false) }
    }
    override fun onActive() {
        super.onActive()
        val active = cm.activeNetwork != null
        postValue(active)
        cm.registerDefaultNetworkCallback(callback)
    }
    override fun onInactive() {
        super.onInactive()
        try { cm.unregisterNetworkCallback(callback) } catch (_: Exception) {}
    }
}
