package com.example.mymessenger.data

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.work.*
import com.example.mymessenger.model.Message
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import java.util.concurrent.TimeUnit


class MessageRepository(context: Context) {

    private val apiService = ApiService.create()
    private val db = AppDatabase.getDatabase(context)
    private val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    private val appContext = context.applicationContext


    @RequiresApi(Build.VERSION_CODES.M)
    private fun isNetworkAvailable(): Boolean {
        val network = connectivityManager.activeNetwork ?: return false
        val capabilities = connectivityManager.getNetworkCapabilities(network) ?: return false

        return capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) ||
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    fun getMessages(): Flow<List<Message>> = flow {
        try {
            val messagesFromDb = db.messageDao().getAllMessages()

            if (isNetworkAvailable()) {
                try {
                    val response = apiService.getMessages()
                    if (response.isSuccessful) {
                        val messages = response.body() ?.map { apiMessage ->
                            apiMessage.copy(
                                authorEmail = "user${apiMessage.userId}@example.com",
                                avatarUrl = "https://i.pravatar.cc/150?img=${apiMessage.userId}",
                            )
                        } ?: emptyList()
                        withContext(Dispatchers.IO) {
                            db.messageDao().deleteAllMessages()
                            db.messageDao().insertAll(messages)
                        }
                        Log.d("Repository", "Данные загружены из API: ${messages.size} сообщений")
                    }
                } catch (e: Exception) {
                    Log.e("Repository", "Ошибка загрузки из API: ${e.message}")
                }
            } else {
                Log.d("Repository", "Нет сети, загружаем из базы данных")
            }

            messagesFromDb.collect { messages ->
                emit(messages)
            }

        } catch (e: Exception) {
            Log.e("Repository", "Ошибка в репозитории: ${e.message}")
            emit(emptyList())
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    suspend fun refreshMessages(showNotification: Boolean = false) {
        if (isNetworkAvailable()) {
            try {
                val response = apiService.getMessages()
                if (response.isSuccessful) {
                    val newMessages = response.body() ?: emptyList()

                    withContext(Dispatchers.IO) {
                        val currentMessages = db.messageDao().getAllMessagesSync()

                        val messagesToSave = newMessages.map { newMessage ->
                            val existingMessage = currentMessages.find { it.id == newMessage.id }
                            newMessage.copy(
                                authorEmail = "user${newMessage.userId}@example.com",
                                avatarUrl = "https://i.pravatar.cc/150?img=${newMessage.userId}",
                                isLiked = existingMessage?.isLiked ?: false
                            )
                        }

                        db.messageDao().deleteAllMessages()
                        db.messageDao().insertAll(messagesToSave)
                    }

                    Log.d("Repository", "Данные обновлены: ${newMessages.size} сообщений")

                    if (showNotification) {
                        showSyncNotification()
                    }
                }
            } catch (e: Exception) {
                Log.e("Repository", "Ошибка при обновлении: ${e.message}")
                throw e
            }
        } else {
            Log.d("Repository", "Нет сети для обновления")
        }
    }

    suspend fun likeMessage(messageId: Int, isLiked: Boolean) {
        withContext(Dispatchers.IO) {
            val message = db.messageDao().getMessageById(messageId)
            message?.let {
                val updatedMessage = it.copy(isLiked = isLiked)
                db.messageDao().updateMessage(updatedMessage)
            }
        }
    }

    private fun showSyncNotification() {
        val notificationManager = appContext
            .getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                "sync_channel",
                "Синхронизация сообщений",
                NotificationManager.IMPORTANCE_DEFAULT
            ).apply {
                description = "Уведомления о новых сообщениях"
            }
            notificationManager.createNotificationChannel(channel)
        }

        val notification = NotificationCompat.Builder(appContext, "sync_channel")
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .setContentTitle("Новые данные получены")
            .setContentText("Список сообщений успешно обновлен")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setAutoCancel(true)
            .build()

        notificationManager.notify(1, notification)
    }

    fun schedulePeriodicSync() {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .setRequiresBatteryNotLow(true)
            .build()

        val syncRequest = PeriodicWorkRequestBuilder<SyncWorker>(
            15, TimeUnit.MINUTES,
            5, TimeUnit.MINUTES
        )
            .setConstraints(constraints)
            .addTag("message_sync")
            .build()

        WorkManager.getInstance(appContext)
            .enqueueUniquePeriodicWork(
                "message_sync_work",
                ExistingPeriodicWorkPolicy.KEEP,
                syncRequest
            )
    }
}
