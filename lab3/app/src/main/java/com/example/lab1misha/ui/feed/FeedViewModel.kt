package com.example.lab1misha.ui.feed

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.lab1misha.data.Message
import com.example.lab1misha.data.MessageRepository
import kotlinx.coroutines.launch

class FeedViewModel(application: Application) : AndroidViewModel(application) {
    private val TAG = "FeedViewModel"
    private val repository = MessageRepository(application)

    private val _messages = MutableLiveData<List<Message>>()
    val messages: LiveData<List<Message>> = _messages

    init {
        Log.d(TAG, "ViewModel создана")
        loadMessages()
    }

    fun loadMessages() {
        viewModelScope.launch {
            Log.d(TAG, "Загрузка сообщений")
            try {
                val msgs = repository.getMessages()
                _messages.value = msgs
                Log.d(TAG, "Загружено сообщений: ${msgs.size}")
            } catch (e: Exception) {
                Log.e(TAG, "Ошибка при загрузке сообщений: ${e.message}")
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        Log.d(TAG, "ViewModel уничтожена")
    }
}
