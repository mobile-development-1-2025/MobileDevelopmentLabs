package com.dak0ta.learnity.app.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build

object QuotesNotificationChannel {

    fun create(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                QuotesNotificationManager.CHANNEL_ID,
                "Updating quotes",
                NotificationManager.IMPORTANCE_DEFAULT,
            ).apply {
                description = "Notifications about quote updates"
            }

            val manager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            manager.createNotificationChannel(channel)
        }
    }
}
