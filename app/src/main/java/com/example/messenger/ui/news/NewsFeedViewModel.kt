package com.example.messenger.ui.news

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.messenger.data.MessageRepository
import com.example.messenger.data.local.MessageEntity
import com.example.messenger.utils.NetworkMonitor
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class NewsFeedViewModel(
    private val repository: MessageRepository,
    private val networkMonitor: NetworkMonitor
) : ViewModel() {

    private val _messages = MutableLiveData<List<MessageEntity>>(emptyList())
    val messages: LiveData<List<MessageEntity>> = _messages

    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean> = _isLoading

    private val _error = MutableLiveData<String?>(null)
    val error: LiveData<String?> = _error

    val isOnline: LiveData<Boolean> = networkMonitor.isOnline

    init {
        observeMessages()
    }

    private fun observeMessages() {
        viewModelScope.launch {
            try {
                repository.messages.collectLatest { list ->
                    _messages.postValue(list)
                }
            } catch (e: Exception) {
                _error.value = "Ошибка загрузки данных: ${e.message}"
            }
        }
    }

    fun refresh() {
        viewModelScope.launch {
            if (networkMonitor.isOnline.value == false) {
                _error.value = "Нет подключения к интернету"
                return@launch
            }
            _isLoading.value = true
            _error.value = null
            try {
                repository.refreshMessages()
            } catch (e: Exception) {
                _error.value = e.message ?: "Неизвестная ошибка"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun toggleLike(messageId: Long, isLiked: Boolean) {
        viewModelScope.launch {
            repository.toggleLike(messageId, isLiked)
        }
    }

    class Factory(
        private val repository: MessageRepository,
        private val networkMonitor: NetworkMonitor
    ) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(NewsFeedViewModel::class.java)) {
                return NewsFeedViewModel(repository, networkMonitor) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }

    override fun onCleared() {
        super.onCleared()
        networkMonitor.unregister()
    }
}



