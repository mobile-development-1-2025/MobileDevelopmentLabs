package com.m.cursproject.ui.feed

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.m.cursproject.data.model.Message
import com.m.cursproject.data.repository.MessageRepository
import com.m.cursproject.workers.SyncWorker
import kotlinx.coroutines.launch
import androidx.work.*
import java.util.concurrent.TimeUnit

class FeedViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = MessageRepository.getInstance(application)
    private val workManager = WorkManager.getInstance(application)

    val messages: LiveData<List<Message>> = repository.allMessages

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _errorMessage = MutableLiveData<String?>()
    val errorMessage: LiveData<String?> = _errorMessage

    init {
        Log.d(TAG, "FeedViewModel: created")
        loadMessages()
        setupPeriodicSync()
    }

    fun loadMessages() {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null

            val result = repository.refreshMessages()

            result.onSuccess {
                Log.d(TAG, "Messages loaded successfully")
            }.onFailure { error ->
                Log.e(TAG, "Failed to load messages: ${error.message}")
                _errorMessage.value = "Не удалось загрузить сообщения. Показаны данные из кэша."
            }

            _isLoading.value = false
        }
    }

    fun toggleLike(message: Message) {
        viewModelScope.launch {
            val result = repository.toggleLike(message)
            result.onSuccess {
                Log.d(TAG, "Like toggled for message ${message.id}")
            }.onFailure { error ->
                Log.e(TAG, "Failed to toggle like: ${error.message}")
            }
        }
    }

    private fun setupPeriodicSync() {
        // Настройка ограничений для синхронизации
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .setRequiresBatteryNotLow(true)
            .build()

        // Периодическая синхронизация каждые 15 минут
        val syncRequest = PeriodicWorkRequestBuilder<SyncWorker>(
            15, TimeUnit.MINUTES
        )
            .setConstraints(constraints)
            .setBackoffCriteria(
                BackoffPolicy.LINEAR,
                WorkRequest.MIN_BACKOFF_MILLIS,
                TimeUnit.MILLISECONDS
            )
            .build()

        // Запускаем периодическую работу
        workManager.enqueueUniquePeriodicWork(
            SyncWorker.WORK_NAME,
            ExistingPeriodicWorkPolicy.KEEP,
            syncRequest
        )

        Log.d(TAG, "Periodic sync setup completed")
    }

    fun cancelPeriodicSync() {
        workManager.cancelUniqueWork(SyncWorker.WORK_NAME)
        Log.d(TAG, "Periodic sync cancelled")
    }

    override fun onCleared() {
        super.onCleared()
        Log.d(TAG, "FeedViewModel: cleared")
    }

    companion object {
        private const val TAG = "FeedViewModel"
    }
}