package com.example.messenger

import android.Manifest
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

    private val TAG = "LifeCycleLog"
    private val viewModel: AppViewModel by viewModels()

    private val requestPermissionsLauncher =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { result ->
            Log.d(TAG, "Permissions result: $result")
        }

    override fun onCreate(savedInstanceState: Bundle?) {

        val isDark = viewModel.isDarkMode.value ?: false
        val initialMode =
            if (isDark) AppCompatDelegate.MODE_NIGHT_YES else AppCompatDelegate.MODE_NIGHT_NO
        if (AppCompatDelegate.getDefaultNightMode() != initialMode) {
            AppCompatDelegate.setDefaultNightMode(initialMode)
        }

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.d(TAG, "MainActivity: onCreate()")

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        val bottomNav = findViewById<com.google.android.material.bottomnavigation.BottomNavigationView>(R.id.bottom_nav)
        bottomNav.setupWithNavController(navController)

        // 1) Канал для уведомлений
        NotificationHelper.createChannel(this)

        // 2) Запрос разрешений (уведомления + контакты)
        requestRuntimePermissions()

        // 3) Планируем периодическую синхронизацию
        scheduleBackgroundSync()
    }

    private fun requestRuntimePermissions() {
        val permissions = mutableListOf<String>()

        // Android 13+ требует отдельное разрешение на уведомления
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            permissions.add(Manifest.permission.POST_NOTIFICATIONS)
        }

        // По заданию “контакты и т.п.” — запросим READ_CONTACTS
        permissions.add(Manifest.permission.READ_CONTACTS)

        requestPermissionsLauncher.launch(permissions.toTypedArray())
    }

    private fun scheduleBackgroundSync() {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED) // синк только при наличии сети
            .build()

        val request = PeriodicWorkRequestBuilder<SyncWorker>(15, TimeUnit.MINUTES)
            .setConstraints(constraints)
            .build()

        WorkManager.getInstance(this).enqueueUniquePeriodicWork(
            "messages_sync_work",
            ExistingPeriodicWorkPolicy.UPDATE,
            request
        )
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "MainActivity: onStart()")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "MainActivity: onResume()")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "MainActivity: onPause()")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "MainActivity: onStop()")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "MainActivity: onDestroy()")
    }
}
