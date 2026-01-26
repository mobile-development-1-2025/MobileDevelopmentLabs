package com.example.lab1.util

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import com.example.lab1.R

object NotificationHelper {
    const val CHANNEL_ID = "news_sync_channel"
    const val CHANNEL_NAME = "Sync notifications"

    fun createChannel(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val ch = NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT)
            ch.description = "Уведомления о новых сообщениях"
            val nm = context.getSystemService(NotificationManager::class.java)
            nm.createNotificationChannel(ch)
        }
    }

    fun showNotification(context: Context, text: String) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            val permission = android.Manifest.permission.POST_NOTIFICATIONS
            if (ContextCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                return
            }
        }

        val nm = NotificationManagerCompat.from(context)
        val n = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_baseline_notifications_24)
            .setContentTitle("Title")
            .setContentText(text)
            .setAutoCancel(true)
            .build()
        nm.notify(1001, n)
    }
}
