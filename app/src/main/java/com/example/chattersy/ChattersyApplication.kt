package com.example.chattersy

import android.app.Application
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.os.Build
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.example.chattersy.worker.SyncWorker

class ChattersyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        registerNetworkCallback()
    }

    private fun registerNetworkCallback() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) return
        val cm = getSystemService(ConnectivityManager::class.java) ?: return
        val request = NetworkRequest.Builder()
            .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            .build()
        cm.registerDefaultNetworkCallback(object : ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) {
                val work = OneTimeWorkRequestBuilder<SyncWorker>().build()
                WorkManager.getInstance(this@ChattersyApplication).enqueueUniqueWork(
                    "chattersy_sync_once",
                    ExistingWorkPolicy.REPLACE,
                    work
                )
            }
        })
    }
}
