package com.waycooler.messengermih.data.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import java.util.*

object NotificationHelper {

    const val CHANNEL_ID = "sync_channel"

    fun createChannel(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                "Синхронизация сообщений",
                NotificationManager.IMPORTANCE_DEFAULT
            ).apply {
                description = "Уведомления об обновлении сообщений"
            }

            val manager =
                context.getSystemService(Context.NOTIFICATION_SERVICE)
                        as NotificationManager

            manager.createNotificationChannel(channel)
        }
    }

    fun showNotification(context: Context) {
        val notificationId = Random().nextInt(10000)

        val notification = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(android.R.drawable.stat_notify_sync)
            .setContentTitle("MessengerMih")
            .setContentText("Новые данные получены")
            .setAutoCancel(true)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .build()

        val manager =
            context.getSystemService(Context.NOTIFICATION_SERVICE)
                    as NotificationManager

        manager.notify(notificationId, notification)
    }
}