package com.example.lab_1

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import com.example.lab_1.work.WorkScheduler

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()
        WorkScheduler.schedule(this)
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                SYNC_CHANNEL_ID,
                "Messages Sync",
                NotificationManager.IMPORTANCE_DEFAULT
            ).apply {
                description = "Notifications about background message sync"
            }
            getSystemService(NotificationManager::class.java)
                .createNotificationChannel(channel)
        }
    }

    companion object {
        const val SYNC_CHANNEL_ID = "sync_channel"
    }
}
