package com.example.messengerlab.ui.feed

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.messengerlab.data.model.MessageEntity
import com.example.messengerlab.data.repository.MessageRepository
import kotlinx.coroutines.launch
import android.util.Log

class FeedViewModel(private val repository: MessageRepository) : ViewModel() {

    private val _messages = MutableLiveData<List<MessageEntity>>()
    val messages: LiveData<List<MessageEntity>> = _messages

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> = _error

    init {
        loadMessages(forceReload = false)
    }

    fun loadMessages(forceReload: Boolean = false) {
        viewModelScope.launch {
            try {
                repository.refreshMessages()
                val list = repository.getAllMessages()
                _messages.value = list
            } catch (e: Exception) {
            }
        }
    }
}