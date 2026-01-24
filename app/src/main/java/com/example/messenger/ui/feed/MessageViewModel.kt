package com.example.mymessenger.ui.feed

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.example.mymessenger.data.MessageRepository
import com.example.mymessenger.data.local.AppDatabase
import com.example.mymessenger.data.local.MessageEntity
import kotlinx.coroutines.launch

class MessageViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: MessageRepository

    private val _messages = MutableLiveData<List<MessageEntity>>()
    val messages: LiveData<List<MessageEntity>> get() = _messages

    init {
        Log.d("MessageViewModel", "created")
        val dao = AppDatabase.getDatabase(application).messageDao()
        repository = MessageRepository(dao)
        loadMessages()
    }

    fun loadMessages() {
        viewModelScope.launch {
            _messages.value = repository.getMessages()
        }
    }

    override fun onCleared() {
        super.onCleared()
        Log.d("MessageViewModel", "cleared")
    }
    fun onLikeClicked(message: MessageEntity) {
        viewModelScope.launch {
            repository.toggleLike(message)
            _messages.value = repository.getMessages()
        }
    }

}
