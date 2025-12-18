package com.example.messenger

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class MessagesViewModel(application: Application) : AndroidViewModel(application) {
    companion object {
        private const val TAG = "MessagesViewModel"
    }

    private val repository = MessageRepository(application)

    private val _messages = repository.getMessages().asLiveData()
    val messages = _messages

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> = _error

    private val _messageCount = MutableLiveData<Int>()
    val messageCount: LiveData<Int> = _messageCount

    init {
        Log.d(TAG, "MessagesViewModel инициализирован")
        loadInitialData()
    }

    override fun onCleared() {
        super.onCleared()
        Log.d(TAG, "MessagesViewModel уничтожен")
    }

    private fun loadInitialData() {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                repository.refreshMessages()

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

                repository.refreshMessages()

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
}