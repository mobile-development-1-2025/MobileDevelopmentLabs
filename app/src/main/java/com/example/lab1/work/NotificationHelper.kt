package com.example.lab1.work

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.lab1.R

object NotificationHelper {

    private const val CHANNEL_ID = "sync_channel"
    private const val NOTIFICATION_ID = 1

    fun show(context: Context, text: String) {
        createChannelIfNeeded(context)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            val granted = context.checkSelfPermission(Manifest.permission.POST_NOTIFICATIONS) ==
                    PackageManager.PERMISSION_GRANTED
            if (!granted) return
        }

        val notification = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle(context.getString(R.string.app_name))
            .setContentText(text)
            .setAutoCancel(true)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .build()

        NotificationManagerCompat.from(context).notify(NOTIFICATION_ID, notification)
    }

    private fun createChannelIfNeeded(context: Context) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) return

        val nm = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val existing = nm.getNotificationChannel(CHANNEL_ID)
        if (existing != null) return

        val channel = NotificationChannel(
            CHANNEL_ID,
            "Синхронизация сообщений",
            NotificationManager.IMPORTANCE_HIGH
        )
        nm.createNotificationChannel(channel)
    }
}
