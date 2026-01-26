package com.example.lab1.ui.feed

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lab1.data.db.MessageEntity
import com.example.lab1.data.repo.MessageRepository
import kotlinx.coroutines.launch

class FeedViewModel(
    private val repository: MessageRepository
) : ViewModel() {

    private val tag = "FeedVM"

    val messages: LiveData<List<MessageEntity>> = repository.observeMessages()

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
}
