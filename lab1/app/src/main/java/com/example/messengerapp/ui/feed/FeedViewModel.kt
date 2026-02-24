package com.example.messengerapp.ui.feed

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.messengerapp.data.local.AppDatabase
import com.example.messengerapp.data.local.MessageEntity
import com.example.messengerapp.data.repository.MessageRepository
import kotlinx.coroutines.launch

class FeedViewModel(application: Application) : AndroidViewModel(application) {

    private val TAG = "FeedViewModel"
    private val repository = MessageRepository(application)
    private val dao = AppDatabase.getInstance(application).messageDao()

    private val _messages = MutableLiveData<List<MessageEntity>>()
    val messages: LiveData<List<MessageEntity>> = _messages

    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean> = _isLoading

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> = _error

    init {
        loadMessages()
    }

    fun loadMessages(forceRefresh: Boolean = false) {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            try {
                val result = repository.getMessages(forceRefresh)
                _messages.value = result
            } catch (e: Exception) {
                _error.value = "Ошибка: ${e.message}"
                Log.e(TAG, "Ошибка загрузки: ${e.message}")
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun sendMessage(text: String) {
        viewModelScope.launch {
            try {
                val newId = -(System.currentTimeMillis() % Int.MAX_VALUE).toInt()
                val newMessage = MessageEntity(
                    id = newId,
                    userId = 0,
                    title = "Я",
                    body = text
                )
                dao.insertAll(listOf(newMessage))
                _messages.value = dao.getAllMessages()
            } catch (e: Exception) {
                _error.value = "Ошибка отправки: ${e.message}"
                Log.e(TAG, "Ошибка отправки: ${e.message}")
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        Log.d(TAG, "ViewModel cleared")
    }
}