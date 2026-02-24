package com.example.messengerapp.ui.feed

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.messengerapp.data.local.MessageEntity
import com.example.messengerapp.data.repository.MessageRepository
import kotlinx.coroutines.launch

class FeedViewModel(application: Application) : AndroidViewModel(application) {

    private val TAG = "FeedViewModel"
    private val repository = MessageRepository(application)

    private val _messages = MutableLiveData<List<MessageEntity>>()
    val messages: LiveData<List<MessageEntity>> = _messages

    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean> = _isLoading

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> = _error

    init {
        Log.d(TAG, "ViewModel created")
        loadMessages()
    }

    fun loadMessages(forceRefresh: Boolean = false) {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            try {
                val result = repository.getMessages(forceRefresh)
                _messages.value = result
                Log.d(TAG, "Загружено сообщений: ${result.size}")
            } catch (e: Exception) {
                _error.value = "Ошибка: ${e.message}"
                Log.e(TAG, "Ошибка загрузки: ${e.message}")
            } finally {
                _isLoading.value = false
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        Log.d(TAG, "ViewModel cleared")
    }
}
