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
            _isLoading.value = true
            _error.value = null
            repository.getMessages()
                .onSuccess { messages ->
                    _messages.value = messages
                    _isLoading.value = false
                    Log.d(TAG, "Messages loaded: ${messages.size}")
                }
                .onFailure { exception ->
                    _error.value = exception.message
                    _isLoading.value = false
                    Log.e(TAG, "Error loading messages", exception)
                }
        }
    }

    fun refreshMessages() {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            repository.refreshMessages()
                .onSuccess { messages ->
                    _messages.value = messages
                    _isLoading.value = false
                    Log.d(TAG, "Messages refreshed: ${messages.size}")
                }
                .onFailure { exception ->
                    _error.value = exception.message
                    _isLoading.value = false
                    Log.e(TAG, "Error refreshing messages", exception)
                }
        }
    }

    override fun onCleared() {
        super.onCleared()
        Log.d(TAG, "ViewModel cleared")
    }
}
