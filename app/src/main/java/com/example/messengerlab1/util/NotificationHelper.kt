package com.example.messengerlab1.util

import android.R
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat

object NotificationHelper {
    const val CHANNEL_ID = "sync_channel"

    fun ensureChannel(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                "Sync notifications",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            val nm = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            nm.createNotificationChannel(channel)
        }
    }

    fun showSyncSuccess(context: Context) {
        ensureChannel(context)
        val nm = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val n = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.stat_notify_sync)
            .setContentTitle("Синхронизация")
            .setContentText("Новые данные получены")
            .setAutoCancel(true)
            .build()

        nm.notify(1001, n)
    }
}