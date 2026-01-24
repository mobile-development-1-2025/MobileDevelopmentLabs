package com.example.myapplication

import android.os.Bundle
import android.util.Log
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.work.*
import com.example.myapplication.worker.SyncMessagesWorker
import java.util.concurrent.TimeUnit

class MainActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.i("Lifecycle", "MainActivity onCreate")
        val navController = findNavController(R.id.nav_host_fragment)
        val bottomNav = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottomNav.setupWithNavController(navController)
        startTestWorker()
    }

    private fun startTestWorker() {
        val request = OneTimeWorkRequestBuilder<SyncMessagesWorker>()
            .setInitialDelay(10, TimeUnit.SECONDS)
            .build()

        WorkManager.getInstance(this).enqueue(request)

        Log.i("WorkManager", "ТЕСТ: воркер запущен из MainActivity")
    }

    override fun onStart() {
        super.onStart(); Log.i("Lifecycle", "MainActivity onStart")
    }

    override fun onResume() {
        super.onResume(); Log.i("Lifecycle", "MainActivity onResume")
    }

    override fun onPause() {
        super.onPause(); Log.i("Lifecycle", "MainActivity onPause")
    }

    override fun onStop() {
        super.onStop(); Log.i("Lifecycle", "MainActivity onStop")
    }

    override fun onDestroy() {
        super.onDestroy(); Log.i("Lifecycle", "MainActivity onDestroy")
    }

    // настоящий метод, но у него мин интервал - 15 минут
    private fun setupPeriodicSync() {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val syncRequest = PeriodicWorkRequestBuilder<SyncMessagesWorker>(
            repeatInterval = 15,
            repeatIntervalTimeUnit = TimeUnit.MINUTES
        )
            .setConstraints(constraints)
            .setBackoffCriteria(
                BackoffPolicy.LINEAR,
                WorkRequest.MIN_BACKOFF_MILLIS,
                TimeUnit.MILLISECONDS
            )
            .build()

        WorkManager.getInstance(this).enqueueUniquePeriodicWork(
            SyncMessagesWorker.WORK_NAME,
            ExistingPeriodicWorkPolicy.KEEP,
            syncRequest
        )

        Log.i("WorkManager", "Периодическая синхронизация настроена")
    }
}