package com.example.mymessenger

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext

class MessageRepository(context: Context) {

    private val apiService = ApiService.create()
    private val db = AppDatabase.getDatabase(context)
    private val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

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
                        val messages = response.body() ?: emptyList()
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
    suspend fun refreshMessages() {
        if (isNetworkAvailable()) {
            try {
                val response = apiService.getMessages()
                if (response.isSuccessful) {
                    val messages = response.body() ?: emptyList()
                    withContext(Dispatchers.IO) {
                        db.messageDao().deleteAllMessages()
                        db.messageDao().insertAll(messages)
                    }
                    Log.d("Repository", "Данные обновлены: ${messages.size} сообщений")
                }
            } catch (e: Exception) {
                Log.e("Repository", "Ошибка при обновлении: ${e.message}")
                throw e
            }
        } else {
            Log.d("Repository", "Нет сети для обновления")
        }
    }

    suspend fun deleteMessage(message: Message) {
        withContext(Dispatchers.IO) {
            db.messageDao().deleteMessage(message)
        }
    }
}