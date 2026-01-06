package com.example.oktimessenger

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import kotlinx.coroutines.launch

class NewsViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = MessageRepository(application)

    private val _messages = MutableLiveData<List<MessageEntity>>()
    val messages: LiveData<List<MessageEntity>> = _messages

    fun loadMessages() {
        viewModelScope.launch {
            try {
                Log.d("NewsViewModel", "loadMessages() called")
                val data = repository.getMessages()
                Log.d("NewsViewModel", "Messages loaded: ${data.size}")
                _messages.value = data
            } catch (e: Exception) {
                Log.e("NewsViewModel", "Ошибка загрузки сообщений", e)
            }
        }
    }

    fun likeMessage(message: MessageEntity) {
        viewModelScope.launch {
            val updatedMessage = repository.toggleLike(message)
            _messages.value = _messages.value?.map {
                if (it.id == updatedMessage.id) updatedMessage else it
            }
        }
    }
}


