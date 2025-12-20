package com.example.messengerlab1.ui.feed

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.messengerlab1.data.db.MessageEntity
import com.example.messengerlab1.data.repo.MessageRepository
import kotlinx.coroutines.launch

class FeedViewModel(
    private val repository: MessageRepository
) : ViewModel() {

    private val tag = "FeedVM"

    val messages: LiveData<List<MessageEntity>> = repository.observeMessages()

    init {
        Log.d(tag, "init: created")
    }

    fun refresh() {
        Log.d(tag, "refresh: clicked")
        viewModelScope.launch {
            try {
                repository.refresh()
                Log.d(tag, "refresh: success")
            } catch (e: Exception) {
                Log.d(tag, "refresh: failed -> ${e.message}")
            }
        }
    }

    override fun onCleared() {
        Log.d(tag, "onCleared: destroyed")
        super.onCleared()
    }
}
