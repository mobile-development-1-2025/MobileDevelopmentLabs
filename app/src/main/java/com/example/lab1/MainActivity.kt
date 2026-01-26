package com.example.lab1

import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.example.lab1.ui.settings.SettingsViewModel
import com.example.lab1.worker.SyncMessagesWorker
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

    private val settingsViewModel: SettingsViewModel by viewModels()

    fun scheduleMessageSync(context: Context) {
        val workRequest = PeriodicWorkRequestBuilder<SyncMessagesWorker>(
            1, TimeUnit.MINUTES
        ).build()

        WorkManager.getInstance(context).enqueueUniquePeriodicWork(
            "sync_messages",
            ExistingPeriodicWorkPolicy.KEEP,
            workRequest
        )
    }

    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                Log.i("Permissions", "Разрешение на уведомления предоставлено")
            } else {
                Log.w("Permissions", "Разрешение на уведомления отклонено")
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navController = findNavController(R.id.nav_host_fragment)
        val bottomNav = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        bottomNav.setupWithNavController(navController)

        settingsViewModel.isDarkMode.observe(this) { enabled ->
            val mode = if (enabled) AppCompatDelegate.MODE_NIGHT_YES
            else AppCompatDelegate.MODE_NIGHT_NO
            AppCompatDelegate.setDefaultNightMode(mode)
        }

        scheduleMessageSync(this)
        Log.i("Lifecycle", "MainActivity onCreate")
    }

    override fun onStart() {
        super.onStart();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(
                    this,
                    android.Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                requestPermissionLauncher.launch(android.Manifest.permission.POST_NOTIFICATIONS)
            }
        }
        Log.i("Lifecycle", "MainActivity onStart")
    }

    override fun onResume() {
        super.onResume();
        Log.i("Lifecycle", "MainActivity onResume")
    }

    override fun onPause() {
        super.onPause();
        Log.i("Lifecycle", "MainActivity onPause")
    }

    override fun onStop() {
        super.onStop();
        Log.i("Lifecycle", "MainActivity onStop")
    }

    override fun onDestroy() {
        super.onDestroy();
        Log.i("Lifecycle", "MainActivity onDestroy")
    }
}
