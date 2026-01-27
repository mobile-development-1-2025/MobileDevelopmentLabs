package com.example.messenger.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.util.Log
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.distinctUntilChanged

/**
 * Монитор состояния сети
 * Отслеживает изменения подключения к интернету
 */
class NetworkMonitor(private val context: Context) {
    
    private val TAG = "NetworkMonitor"
    private val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    /**
     * Проверка текущего состояния подключения
     */
    fun isConnected(): Boolean {
        val network = connectivityManager.activeNetwork
        val capabilities = connectivityManager.getNetworkCapabilities(network)
        val isConnected = capabilities?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) == true
        Log.d(TAG, "Состояние сети: ${if (isConnected) "Онлайн" else "Офлайн"}")
        return isConnected
    }

    /**
     * Поток для отслеживания изменений состояния сети
     */
    val networkState: Flow<NetworkState> = callbackFlow {
        val networkCallback = object : ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) {
                Log.d(TAG, "Сеть доступна")
                trySend(NetworkState.Connected)
            }

            override fun onLost(network: Network) {
                Log.d(TAG, "Сеть потеряна")
                trySend(NetworkState.Disconnected)
            }

            override fun onCapabilitiesChanged(
                network: Network,
                networkCapabilities: NetworkCapabilities
            ) {
                val hasInternet = networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
                Log.d(TAG, "Изменились возможности сети. Интернет: $hasInternet")
                if (hasInternet) {
                    trySend(NetworkState.Connected)
                }
            }
        }

        val networkRequest = NetworkRequest.Builder()
            .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            .build()

        connectivityManager.registerNetworkCallback(networkRequest, networkCallback)
        
        // Отправляем начальное состояние
        trySend(if (isConnected()) NetworkState.Connected else NetworkState.Disconnected)

        awaitClose {
            Log.d(TAG, "Отключение мониторинга сети")
            connectivityManager.unregisterNetworkCallback(networkCallback)
        }
    }.distinctUntilChanged()

    /**
     * Состояния сети
     */
    sealed class NetworkState {
        object Connected : NetworkState()
        object Disconnected : NetworkState()
    }
}
