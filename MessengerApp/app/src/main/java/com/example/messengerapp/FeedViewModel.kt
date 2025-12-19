package com.example.messengerapp

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class FeedViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: MessageRepository

    val messages: LiveData<List<MessageEntity>>

    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean> get() = _isLoading

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> get() = _error

    init {
        Log.d("FeedViewModel", "init: created")

        val db = AppDatabase.getInstance(application)
        repository = MessageRepository(
            api = ApiClient.messageApi,
            dao = db.messageDao(),
        )

        messages = repository.messagesFlow.asLiveData()

        refreshMessages()
    }

    fun refreshMessages() {
        viewModelScope.launch {
            Log.d("FeedViewModel", "refreshMessages: start")
            _isLoading.value = true
            _error.value = null

            try {
                repository.refreshMessages()
                Log.d("FeedViewModel", "refreshMessages: success")
            } catch (e: Exception) {
                Log.e("FeedViewModel", "refreshMessages: error", e)
                _error.value = e.message
            } finally {
                _isLoading.value = false
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        Log.d("FeedViewModel", "onCleared: destroyed")
    }
}
