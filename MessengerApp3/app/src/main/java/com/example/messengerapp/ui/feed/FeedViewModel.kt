package com.example.messenger.ui.feed

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.messenger.data.AppDatabase
import com.example.messenger.data.Message
import com.example.messenger.data.MessageRepository
import com.example.messenger.data.RetrofitInstance
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class FeedViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = MessageRepository(RetrofitInstance.api, AppDatabase.getDatabase(application))

    private val _messages = MutableStateFlow<List<Message>>(emptyList())
    val messages: StateFlow<List<Message>> get() = _messages

    private val limit = 20

    fun loadMessages() {
        viewModelScope.launch {
            try {
                Log.d("FeedViewModel", "Loading messages")
                val data = repository.fetchMessages(limit)
                _messages.value = data
                Log.d("FeedViewModel", "Loaded ${data.size} messages")
            } catch (e: Exception) {
                Log.e("FeedViewModel", "Error loading messages", e)
            }
        }
    }

    fun refresh() {
        Log.d("FeedViewModel", "Refresh button clicked")
        loadMessages()
    }
}