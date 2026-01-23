package com.example.messenger.ui.feed

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.messenger.data.AppDatabase
import com.example.messenger.data.Message
import com.example.messenger.data.MessageRepository
import com.example.messenger.data.RetrofitInstance
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class FeedViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = MessageRepository(
        RetrofitInstance.api,
        AppDatabase.getDatabase(application)
    )

    private val _messages = MutableStateFlow<List<Message>>(emptyList())
    val messages: StateFlow<List<Message>> = _messages.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val limit = 20

    init {
        observeMessages()
        loadMessages()
    }

    private fun observeMessages() {
        viewModelScope.launch {
            repository.getMessagesFlow().collect { messages ->
                _messages.value = messages
            }
        }
    }

    fun loadMessages() {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                Log.d("FeedViewModel", "Загрузка сообщений")
                repository.fetchMessages(limit)
                Log.d("FeedViewModel", "Сообщения загружены")
            } catch (e: Exception) {
                Log.e("FeedViewModel", "Ошибка загрузки сообщений", e)
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun toggleLike(message: Message) {
        viewModelScope.launch {
            try {
                repository.toggleLike(message)
                Log.d("FeedViewModel", "Лайк переключен для сообщения ${message.id}")
            } catch (e: Exception) {
                Log.e("FeedViewModel", "Ошибка переключения лайка", e)
            }
        }
    }

    fun toggleDislike(message: Message) {
        viewModelScope.launch {
            try {
                repository.toggleDislike(message)
                Log.d("FeedViewModel", "Дизлайк переключен для сообщения ${message.id}")
            } catch (e: Exception) {
                Log.e("FeedViewModel", "Ошибка переключения дизлайка", e)
            }
        }
    }

    fun refresh() {
        Log.d("FeedViewModel", "Обновление данных")
        loadMessages()
    }
}
