package com.example.messenger

import android.app.Application
import android.util.Log
import com.example.messenger.notifications.WorkerManager

class MainApp: Application() {
    companion object {
        const val tag = "Main App"
    }

    private lateinit var workerManager: WorkerManager

    override fun onCreate() {
        Log.d(tag, "onCreate: Создание приложения...")

        super.onCreate()
        workerManager = WorkerManager(this)

        workerManager.startImmediateLoad()
        workerManager.scheduleMessageWorker()
    }
}
