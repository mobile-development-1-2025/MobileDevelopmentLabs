package com.example.lab_1.work

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.lab_1.App
import com.example.lab_1.R
import com.example.lab_1.di.ServiceLocator

class SyncMessagesWorker(
    appContext: Context,
    params: WorkerParameters
) : CoroutineWorker(appContext, params) {

    override suspend fun doWork(): Result {
        Log.d(TAG, "doWork() started")

        val repo = ServiceLocator.provideMessageRepository(applicationContext)
        val result = runCatching { repo.refreshMessages() }
            .getOrElse {
                Log.e(TAG, "refreshMessages() crashed", it)
                return Result.retry()
            }

        Log.d(TAG, "refreshMessages result=$result")

        return if (result.isSuccess) {
            showNewDataNotification()
            Log.d(TAG, "doWork() success")
            Result.success()
        } else {
            Log.d(TAG, "doWork() retry")
            Result.retry()
        }
    }

    private fun showNewDataNotification() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            val granted = applicationContext.checkSelfPermission(Manifest.permission.POST_NOTIFICATIONS) ==
                    PackageManager.PERMISSION_GRANTED
            if (!granted) {
                Log.d(TAG, "POST_NOTIFICATIONS not granted, skip notify")
                return
            }
        }

        val notif = NotificationCompat.Builder(applicationContext, App.SYNC_CHANNEL_ID)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle("New data is received")
            .setContentText("Messages updated from network")
            .setAutoCancel(true)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .build()

        NotificationManagerCompat.from(applicationContext).notify(1001, notif)
        Log.d(TAG, "notification shown")
    }

    companion object {
        private const val TAG = "SyncMessagesWorker"
    }
}
