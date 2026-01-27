package com.example.messenger

import android.app.Application
import android.util.Log
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.example.messenger.utils.NotificationHelper
import com.example.messenger.worker.SyncWorker
import java.util.concurrent.TimeUnit

/**
 * Application класс для инициализации компонентов приложения
 */
class MessengerApplication : Application() {
    
    private val TAG = "MessengerApplication"

    override fun onCreate() {
        super.onCreate()
        Log.d(TAG, "Приложение инициализировано")
        
        // Создаем канал уведомлений
        NotificationHelper.createNotificationChannel(this)
        
        // Настраиваем периодическую синхронизацию
        setupPeriodicSync()
    }

    /**
     * Настройка периодической фоновой синхронизации с помощью WorkManager
     */
    private fun setupPeriodicSync() {
        Log.d(TAG, "Настройка периодической синхронизации")
        
        // Ограничения для выполнения работы
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED) // Требуется подключение к сети
            .build()

        // Создаем периодический запрос на выполнение работы
        // Минимальный интервал - 15 минут (ограничение WorkManager)
        val syncRequest = PeriodicWorkRequestBuilder<SyncWorker>(
            15, TimeUnit.MINUTES
        )
            .setConstraints(constraints)
            .addTag(SyncWorker.WORK_NAME)
            .build()

        // Регистрируем работу
        WorkManager.getInstance(this).enqueueUniquePeriodicWork(
            SyncWorker.WORK_NAME,
            ExistingPeriodicWorkPolicy.KEEP, // Сохраняем существующую работу если есть
            syncRequest
        )
        
        Log.d(TAG, "Периодическая синхронизация настроена (каждые 15 минут)")
    }
}
