package com.example.messenger.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.messenger.data.AppDatabase
import com.example.messenger.data.Message
import com.example.messenger.repository.MessageRepository
import kotlinx.coroutines.launch

class MessageViewModel(application: Application) : AndroidViewModel(application) {
    private val TAG = "MessageViewModel"
    
    private val repository: MessageRepository
    
    private val _messages = MutableLiveData<List<Message>>()
    val messages: LiveData<List<Message>> = _messages
    
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading
    
    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> = _error

    init {
        Log.d(TAG, "MessageViewModel инициализирован")
        val database = AppDatabase.getDatabase(application)
        repository = MessageRepository(database.messageDao())
        loadMessages()
    }

    fun loadMessages(forceRefresh: Boolean = false) {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            
            val result = repository.getMessages(forceRefresh)
            
            result.onSuccess { messageList ->
                _messages.value = messageList
                Log.d(TAG, "Сообщения загружены: ${messageList.size}")
            }.onFailure { exception ->
                _error.value = exception.message ?: "Неизвестная ошибка"
                Log.e(TAG, "Ошибка загрузки сообщений: ${exception.message}")
            }
            
            _isLoading.value = false
        }
    }

    fun refreshMessages() {
        Log.d(TAG, "Обновление сообщений по запросу пользователя")
        loadMessages(forceRefresh = true)
    }

    override fun onCleared() {
        super.onCleared()
        Log.d(TAG, "MessageViewModel уничтожен")
    }
}
