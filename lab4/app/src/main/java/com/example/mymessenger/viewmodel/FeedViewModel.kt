package com.example.mymessenger.viewmodel

import androidx.lifecycle.*
import com.example.mymessenger.data.MessageRepository
import com.example.mymessenger.data.local.MessageEntity
import kotlinx.coroutines.launch

class FeedViewModel(
    private val repo: MessageRepository
) : ViewModel() {

    private val _messages = MutableLiveData<List<MessageEntity>>()
    val messages: LiveData<List<MessageEntity>> = _messages

    init {
        refresh(force = false)
    }

    fun refresh(force: Boolean) {
        viewModelScope.launch {
            _messages.value = repo.loadMessages(forceRefresh = force)
        }
    }

    fun toggleLike(item: MessageEntity) {
        viewModelScope.launch {
            repo.toggleLike(item.id)
            refresh(force = false)
        }
    }
}
