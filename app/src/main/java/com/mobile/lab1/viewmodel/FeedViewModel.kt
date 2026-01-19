package com.mobile.lab1.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.mobile.lab1.data.AppDatabase
import com.mobile.lab1.data.Message
import com.mobile.lab1.data.MessageRepository
import com.mobile.lab1.data.NetworkModule
import kotlinx.coroutines.launch

class FeedViewModel(application: Application) : AndroidViewModel(application) {

    companion object {
        private const val TAG = "FeedViewModel"
    }

    private val repository: MessageRepository

    private val _messages = MutableLiveData<List<Message>>(emptyList())
    val messages: LiveData<List<Message>> = _messages

    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean> = _isLoading

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> = _error

    init {
        Log.d(TAG, "init: FeedViewModel created")
        val db = AppDatabase.getInstance(application)
        repository = MessageRepository(NetworkModule.api, db.messageDao())
        refresh(false)
    }

    fun refresh(forceRefresh: Boolean) {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            try {
                val list = repository.loadMessages(forceRefresh)
                _messages.value = list
            } catch (e: Exception) {
                Log.e(TAG, "Failed to load messages", e)
                _error.value = e.message ?: "Ошибка загрузки"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun onLikeClicked(message: Message) {
        viewModelScope.launch {
            repository.toggleLike(message)
            val current = _messages.value ?: return@launch
            _messages.value = current.map {
                if (it.id == message.id) it.copy(isLiked = !message.isLiked) else it
            }
        }
    }

    override fun onCleared() {
        Log.d(TAG, "onCleared: FeedViewModel destroyed")
        super.onCleared()
    }
}