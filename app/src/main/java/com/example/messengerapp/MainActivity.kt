package com.example.messenger

import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkRequest
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import androidx.activity.viewModels
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.example.messengerapp.data.worker.SyncWorker
import com.example.messengerapp.ui.AppViewModel
import com.google.android.material.snackbar.Snackbar
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {
    private val appVM: AppViewModel by viewModels()
    private val notifPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { }

    private lateinit var connectivityManager: ConnectivityManager
    private lateinit var networkCallback: ConnectivityManager.NetworkCallback

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        connectivityManager =
            getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager

        val req = NetworkRequest.Builder().build()

        networkCallback = object : ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) {
                runOnUiThread {
                    Snackbar.make(
                        findViewById(android.R.id.content),
                        "Онлайн",
                        Snackbar.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onLost(network: Network) {
                runOnUiThread {
                    Snackbar.make(
                        findViewById(android.R.id.content),
                        "Оффлайн: показан кеш",
                        Snackbar.LENGTH_SHORT
                    ).show()
                }
            }
        }

        connectivityManager.registerNetworkCallback(req, networkCallback)

        appVM.isDark.observe(this) { isDark ->
            AppCompatDelegate.setDefaultNightMode(
                if (isDark) AppCompatDelegate.MODE_NIGHT_YES
                else AppCompatDelegate.MODE_NIGHT_NO
            )
        }

        val navHost = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHost.navController
        val bottom = findViewById<BottomNavigationView>(R.id.bottom_nav)
        bottom.setupWithNavController(navController)

        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val request = PeriodicWorkRequestBuilder<SyncWorker>(15, TimeUnit.MINUTES)
            .setConstraints(constraints)
            .build()

        WorkManager.getInstance(this).enqueueUniquePeriodicWork(
            "sync_messages",
            ExistingPeriodicWorkPolicy.UPDATE,
            request
        )

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            notifPermissionLauncher.launch(android.Manifest.permission.POST_NOTIFICATIONS)
        }

        Log.d("Lifecycle", "MainActivity onCreate")
    }

    override fun onStart() { super.onStart(); Log.d("Lifecycle", "MainActivity onStart") }
    override fun onResume() { super.onResume(); Log.d("Lifecycle", "MainActivity onResume") }
    override fun onPause() { Log.d("Lifecycle", "MainActivity onPause"); super.onPause() }
    override fun onStop() { Log.d("Lifecycle", "MainActivity onStop"); super.onStop() }
    override fun onDestroy() {
        connectivityManager.unregisterNetworkCallback(networkCallback)
        Log.d("Lifecycle", "MainActivity onDestroy")
        super.onDestroy()
    }
}
