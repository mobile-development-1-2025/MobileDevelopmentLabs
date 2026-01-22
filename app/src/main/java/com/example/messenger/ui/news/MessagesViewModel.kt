package com.example.messenger.ui.news

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.work.WorkManager
import com.example.messenger.data.repository.MessageRepository
import com.example.messenger.worker.SyncWorker
import kotlinx.coroutines.launch

class MessagesViewModel(application: Application) : AndroidViewModel(application) {
    companion object {
        private const val TAG = "MessagesViewModel"
    }

    private val repository = MessageRepository(application)
    private val workManager = WorkManager.getInstance(application)

    private val _messages = repository.getMessages().asLiveData()
    val messages: LiveData<List<com.example.messenger.data.local.MessageEntity>> = _messages

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> = _error

    private val _messageCount = MutableLiveData<Int>()
    val messageCount: LiveData<Int> = _messageCount

    private val _syncResult = MutableLiveData<Boolean?>()
    val syncResult: LiveData<Boolean?> = _syncResult

    init {
        Log.d(TAG, "MessagesViewModel инициализирован")
        loadInitialData()
        startPeriodicSync()
    }

    override fun onCleared() {
        super.onCleared()
        Log.d(TAG, "MessagesViewModel уничтожен")
    }

    private fun loadInitialData() {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                val success = repository.refreshMessages()
                _syncResult.value = success

                val count = repository.getMessageCount()
                _messageCount.value = count
                Log.d(TAG, "Загружено $count сообщений")
            } catch (e: Exception) {
                _error.value = "Ошибка загрузки: ${e.message}"
                Log.e(TAG, "Ошибка при начальной загрузке: ${e.message}")
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun refreshMessages() {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                _error.value = null
                _syncResult.value = null

                val success = repository.refreshMessages()
                _syncResult.value = success

                val count = repository.getMessageCount()
                _messageCount.value = count
                Log.d(TAG, "Ручное обновление: загружено $count сообщений")
            } catch (e: Exception) {
                _error.value = "Ошибка обновления: ${e.message}"
                Log.e(TAG, "Ошибка при ручном обновлении: ${e.message}")
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun toggleLike(messageId: Int, currentIsLiked: Boolean) {
        viewModelScope.launch {
            try {
                repository.toggleLike(messageId, currentIsLiked)
            } catch (e: Exception) {
                Log.e(TAG, "Ошибка при обновлении лайка: ${e.message}")
            }
        }
    }

    private fun startPeriodicSync() {
        SyncWorker.startPeriodicSync(workManager)
    }

    fun resetSyncResult() {
        _syncResult.value = null
    }
}