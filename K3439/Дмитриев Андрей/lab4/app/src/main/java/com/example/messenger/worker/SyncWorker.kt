package com.example.messenger.worker

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.messenger.data.AppDatabase
import com.example.messenger.repository.MessageRepository
import com.example.messenger.utils.NotificationHelper

/**
 * Worker для периодической синхронизации сообщений в фоне
 * Выполняется через WorkManager с заданным интервалом
 */
class SyncWorker(
    context: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(context, workerParams) {

    private val TAG = "SyncWorker"

    override suspend fun doWork(): Result {
        Log.d(TAG, "Начало фоновой синхронизации сообщений")
        
        return try {
            val database = AppDatabase.getDatabase(applicationContext)
            val repository = MessageRepository(database.messageDao())
            
            val syncResult = repository.syncMessages()
            
            if (syncResult) {
                Log.d(TAG, "Синхронизация успешно завершена")
                
                // Показываем уведомление об успешной синхронизации
                NotificationHelper.showSyncNotification(applicationContext)
                
                Result.success()
            } else {
                Log.w(TAG, "Синхронизация завершена с ошибкой, повторим позже")
                Result.retry()
            }
        } catch (e: Exception) {
            Log.e(TAG, "Ошибка при синхронизации: ${e.message}")
            Result.failure()
        }
    }
    
    companion object {
        const val WORK_NAME = "sync_messages_work"
    }
}
