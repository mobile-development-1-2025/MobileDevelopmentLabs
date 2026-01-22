package com.example.messenger.worker

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.WorkerParameters
import androidx.work.ExistingPeriodicWorkPolicy
import com.example.messenger.data.repository.MessageRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.concurrent.TimeUnit

class SyncWorker(
    context: Context,
    params: WorkerParameters
) : CoroutineWorker(context, params) {

    companion object {
        private const val TAG = "SyncWorker"
        private const val WORK_NAME = "message_sync_worker"

        fun startPeriodicSync(workManager: WorkManager) {
            val workRequest = PeriodicWorkRequestBuilder<SyncWorker>(
                15, TimeUnit.MINUTES
            )
                .setInitialDelay(5, TimeUnit.MINUTES)
                .build()

            workManager.enqueueUniquePeriodicWork(
                WORK_NAME,
                ExistingPeriodicWorkPolicy.KEEP,
                workRequest
            )
            Log.d(TAG, "Периодическая синхронизация запущена")
        }

        fun stopPeriodicSync(workManager: WorkManager) {
            workManager.cancelUniqueWork(WORK_NAME)
            Log.d(TAG, "Периодическая синхронизация остановлена")
        }
    }

    override suspend fun doWork(): Result {
        Log.d(TAG, "Запущена фоновая синхронизация")

        return try {
            val repository = MessageRepository(applicationContext)
            val success = withContext(Dispatchers.IO) {
                repository.refreshMessages()
            }

            if (success) {
                Log.d(TAG, "Синхронизация успешна")
                Result.success()
            } else {
                Log.d(TAG, "Синхронизация не удалась")
                Result.retry()
            }
        } catch (e: Exception) {
            Log.e(TAG, "Ошибка при синхронизации: ${e.message}", e)
            Result.failure()
        }
    }
}