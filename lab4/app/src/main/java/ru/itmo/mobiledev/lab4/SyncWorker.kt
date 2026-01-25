package ru.itmo.mobiledev.lab4

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters

class SyncWorker(
    appContext: Context,
    params: WorkerParameters
) : CoroutineWorker(appContext, params) {

    override suspend fun doWork(): Result {
        if (AppPreferences.isOffline(applicationContext)) {
            return Result.success()
        }
        val repository = MessageRepository(
            api = ApiClient.api,
            dao = AppDatabase.getInstance(applicationContext).messageDao()
        )
        val result = repository.refresh()
        if (result.isSuccess) {
            NotificationUtils.showSyncNotification(applicationContext)
            return Result.success()
        }
        return Result.retry()
    }
}
