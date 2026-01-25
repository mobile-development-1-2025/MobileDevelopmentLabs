package com.example.myapplication.worker

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.work.CoroutineWorker
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.WorkerParameters
import com.example.myapplication.MainActivity
import com.example.myapplication.R
import com.example.myapplication.data.repository.MessageRepository
import com.example.myapplication.data.repository.NetworkResult
import java.util.concurrent.TimeUnit

class SyncMessagesWorker(
    context: Context,
    params: WorkerParameters
) : CoroutineWorker(context, params) {

    private val repository = MessageRepository(context)

    companion object {
        const val CHANNEL_ID = "sync_channel"
        const val NOTIFICATION_ID = 1001
        const val WORK_NAME = "sync_messages_work"
    }

    override suspend fun doWork(): Result {
        Log.i("SyncWorker", "ТЕСТ: синхронизация началась")

        return try {
            when (val result = repository.fetchMessages(forceRefresh = true)) {
                is NetworkResult.Success -> {
                    val count = result.data.size
                    Log.i("SyncWorker", "ТЕСТ: получено $count сообщений")

                    showNotification(
                        title = "Тестовая синхронизация",
                        message = "Получено сообщений: $count"
                    )

                    scheduleNextTestRun()
                    Result.success()
                }

                is NetworkResult.Error -> {
                    Log.e("SyncWorker", "ТЕСТ: ошибка ${result.message}")
                    scheduleNextTestRun()
                    Result.retry()
                }

                is NetworkResult.Loading -> {
                    scheduleNextTestRun()
                    Result.retry()
                }
            }
        } catch (e: Exception) {
            Log.e("SyncWorker", "ТЕСТ: исключение ${e.message}")
            scheduleNextTestRun()
            Result.failure()
        }
    }

    private fun scheduleNextTestRun() {
        val request = OneTimeWorkRequestBuilder<SyncMessagesWorker>()
            .setInitialDelay(1, TimeUnit.MINUTES)
            .build()

        WorkManager.getInstance(applicationContext).enqueue(request)
        Log.i("SyncWorker", "ТЕСТ: следующий запуск через 1 минуту")
    }

    private fun showNotification(title: String, message: String) {
        val notificationManager =
            applicationContext.getSystemService(Context.NOTIFICATION_SERVICE)
                    as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                "Тестовая синхронизация",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            notificationManager.createNotificationChannel(channel)
        }

        val intent = Intent(applicationContext, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }

        val pendingIntent = PendingIntent.getActivity(
            applicationContext,
            0,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val notification = NotificationCompat.Builder(applicationContext, CHANNEL_ID)
            .setSmallIcon(R.drawable.feed)
            .setContentTitle(title)
            .setContentText(message)
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)
            .build()

        notificationManager.notify(NOTIFICATION_ID, notification)
    }
}
