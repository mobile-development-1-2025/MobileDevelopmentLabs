package com.dak0ta.learnity.app.notification

import android.app.NotificationManager
import android.content.Context
import androidx.core.app.NotificationCompat
import com.dak0ta.learnity.app.R

class QuotesNotificationManager(private val context: Context) {

    fun showQuotesUpdated() {
        val notification = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle("Quotes updated")
            .setContentText("We have uploaded fresh quotes!")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .build()

        val manager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        manager.notify(NOTIFICATION_ID, notification)
    }

    companion object {

        const val CHANNEL_ID = "quotes_updates"
        private const val NOTIFICATION_ID = 1001
    }
}
