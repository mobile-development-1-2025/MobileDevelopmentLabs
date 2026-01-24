package com.example.messenger.ui.feed

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.messenger.data.Message
import com.example.messenger.repository.MessageRepository
import com.example.messenger.util.NetworkUtils
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class FeedViewModel(application: Application) : AndroidViewModel(application) {

    private val TAG = "FeedViewModel"
    private val repository = MessageRepository(application)

    // Messages from database as Flow -> LiveData
    val messages: LiveData<List<Message>> = repository.messagesFlow.asLiveData()

    // Loading state
    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean> = _isLoading

    // Error state
    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> = _error

    // Network state
    val isOnline: LiveData<Boolean> = NetworkUtils.observeNetworkState(application)
        .asLiveData()

    init {
        Log.d(TAG, "ViewModel создана")
        loadMessages()
    }

    fun loadMessages() {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null

            Log.d(TAG, "Загрузка сообщений...")

            val result = repository.refreshMessages()

            if (result.isSuccess) {
                Log.d(TAG, "Загружено сообщений: ${result.getOrDefault(0)}")
            } else {
                val errorMsg = result.exceptionOrNull()?.message ?: "Неизвестная ошибка"
                Log.e(TAG, "Ошибка загрузки: $errorMsg")
                _error.value = errorMsg
            }

            _isLoading.value = false
        }
    }

    fun toggleLike(message: Message) {
        viewModelScope.launch {
            Log.d(TAG, "Переключение лайка для: ${message.id}")
            repository.toggleLike(message.id, !message.isLiked)
        }
    }

    fun clearError() {
        _error.value = null
    }

    override fun onCleared() {
        super.onCleared()
        Log.d(TAG, "ViewModel уничтожена")
    }
}
