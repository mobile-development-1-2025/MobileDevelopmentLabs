package com.example.oktimessenger

import android.app.Application
import androidx.lifecycle.*
import kotlinx.coroutines.launch

class NewsViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = MessageRepository(application)

    private val _messages = MutableLiveData<List<MessageEntity>>()
    val messages: LiveData<List<MessageEntity>> = _messages

    fun loadMessages() {
        viewModelScope.launch {
            _messages.value = repository.getMessages().toList()

        }
    }
}
