package com.example.myapplication.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.local.entity.Message
import com.example.myapplication.data.repository.MessageRepository
import com.example.myapplication.data.repository.NetworkResult
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class FeedViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = MessageRepository(application.applicationContext)

    private val _messages = MutableLiveData<List<Message>>()
    val messages: LiveData<List<Message>> get() = _messages

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> get() = _error

    private val _isRefreshing = MutableLiveData<Boolean>()
    val isRefreshing: LiveData<Boolean> get() = _isRefreshing

    init {
        Log.i("ViewModel", "FeedViewModel created")
        observeMessages()
        loadMessages()
    }

    private fun observeMessages() {
        viewModelScope.launch {
            repository.messages
                .catch { e ->
                    Log.e("ViewModel", "Ошибка Flow: ${e.message}")
                    _error.value = "Ошибка загрузки данных: ${e.message}"
                }
                .collect { messageList ->
                    _messages.value = messageList
                    Log.i("ViewModel", "Обновлено сообщений: ${messageList.size}")
                }
        }
    }

    fun loadMessages(forceRefresh: Boolean = false) {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null

            when (val result = repository.fetchMessages(forceRefresh)) {
                is NetworkResult.Success -> {
                    Log.i("ViewModel", "Успешно загружено ${result.data.size} сообщений")
                }
                is NetworkResult.Error -> {
                    Log.e("ViewModel", "Ошибка: ${result.message}")
                    _error.value = result.message
                }
                is NetworkResult.Loading -> {
                }
            }

            _isLoading.value = false
        }
    }

    fun refreshMessages() {
        viewModelScope.launch {
            _isRefreshing.value = true
            _error.value = null

            when (val result = repository.fetchMessages(forceRefresh = true)) {
                is NetworkResult.Success -> {
                    Log.i("ViewModel", "Данные обновлены")
                }
                is NetworkResult.Error -> {
                    _error.value = result.message
                }
                is NetworkResult.Loading -> {}
            }

            _isRefreshing.value = false
        }
    }

    fun toggleLike(messageId: Int, currentLikeStatus: Boolean) {
        viewModelScope.launch {
            repository.toggleLike(messageId, !currentLikeStatus)
            Log.i("ViewModel", "Лайк изменён: $messageId -> ${!currentLikeStatus}")
        }
    }

    override fun onCleared() {
        super.onCleared()
        Log.i("ViewModel", "FeedViewModel cleared")
    }
}