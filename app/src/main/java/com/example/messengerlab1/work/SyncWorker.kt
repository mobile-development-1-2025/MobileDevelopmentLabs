package com.example.messengerlab1.work

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.messengerlab1.App
import com.example.messengerlab1.work.notify.SyncNotifier

class SyncWorker(
    appContext: Context,
    params: WorkerParameters
) : CoroutineWorker(appContext, params) {

    override suspend fun doWork(): Result {
        val app = applicationContext as App

        return try {
            app.messageRepository.refresh()
            SyncNotifier.show(applicationContext, "Новые данные получены")
            Result.success()
        } catch (e: Exception) {
            Result.retry()
        }
    }
}
