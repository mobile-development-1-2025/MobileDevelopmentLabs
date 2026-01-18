package com.example.messenger.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.messenger.data.Message
import com.example.messenger.repository.MessageRepository
import kotlinx.coroutines.launch

class NewsViewModel(application: Application) : AndroidViewModel(application) {

    companion object {
        private const val TAG = "NewsViewModel"
    }

    private val repository = MessageRepository(application.applicationContext)

    private val _messages = MutableLiveData<List<Message>>()
    val messages: LiveData<List<Message>> = _messages

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> = _error

    init {
        Log.d(TAG, "ViewModel created")
        loadMessages()
    }

    fun loadMessages() {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                _error.value = null
                val result = repository.getMessages()
                if (result.isSuccess) {
                    val messages = result.getOrNull() ?: emptyList()
                    _messages.value = messages
                    _isLoading.value = false
                    Log.d(TAG, "Messages loaded: ${messages.size}")
                } else {
                    val exception = result.exceptionOrNull()
                    _error.value = exception?.message ?: "Unknown error"
                    _isLoading.value = false
                    Log.e(TAG, "Error loading messages", exception)
                }
            } catch (e: Exception) {
                _error.value = e.message ?: "Error loading messages"
                _isLoading.value = false
                Log.e(TAG, "Exception in loadMessages", e)
            }
        }
    }

    fun refreshMessages() {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                _error.value = null
                val result = repository.refreshMessages()
                if (result.isSuccess) {
                    val messages = result.getOrNull() ?: emptyList()
                    _messages.value = messages
                    _isLoading.value = false
                    Log.d(TAG, "Messages refreshed: ${messages.size}")
                } else {
                    val exception = result.exceptionOrNull()
                    _error.value = exception?.message ?: "Unknown error"
                    _isLoading.value = false
                    Log.e(TAG, "Error refreshing messages", exception)
                }
            } catch (e: Exception) {
                _error.value = e.message ?: "Error refreshing messages"
                _isLoading.value = false
                Log.e(TAG, "Exception in refreshMessages", e)
            }
        }
    }

    fun toggleLike(message: Message) {
        viewModelScope.launch {
            val updatedMessage = message.copy(isLiked = !message.isLiked)
            repository.toggleLike(updatedMessage.id, updatedMessage.isLiked)
            val currentMessages = _messages.value?.toMutableList() ?: mutableListOf()
            val index = currentMessages.indexOfFirst { it.id == message.id }
            if (index != -1) {
                currentMessages[index] = updatedMessage
                _messages.value = currentMessages
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        Log.d(TAG, "ViewModel cleared")
    }
}
