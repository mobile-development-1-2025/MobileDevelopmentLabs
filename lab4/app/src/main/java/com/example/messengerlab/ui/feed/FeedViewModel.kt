package com.example.messengerlab.ui.feed

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.messengerlab.data.model.MessageEntity
import com.example.messengerlab.data.repository.MessageRepository
import kotlinx.coroutines.launch

class FeedViewModel(
    private val repository: MessageRepository
) : ViewModel() {

    private val _messages = MutableLiveData<List<MessageEntity>>()
    val messages: LiveData<List<MessageEntity>> = _messages

    private var allMessages: List<MessageEntity> = emptyList()
    private var currentPage = 0
    private val pageSize = 20

    init {
        loadFromDb()
    }

    fun loadNextPage() {
        if (allMessages.isEmpty()) return

        val from = currentPage * pageSize
        val to = minOf(from + pageSize, allMessages.size)

        if (from >= allMessages.size) {
            currentPage = 0
            loadNextPage()
            return
        }

        _messages.value = allMessages.subList(from, to)
        currentPage++
    }

    fun toggleLike(message: MessageEntity) {
        viewModelScope.launch {
            repository.updateMessage(
                message.copy(isLiked = !message.isLiked)
            )
            loadFromDb()
        }
    }

    private fun loadFromDb() {
        viewModelScope.launch {
            allMessages = repository.getAllMessages()
            Log.d("FEED", "Messages from DB = ${allMessages.size}")
            currentPage = 0
            loadNextPage()
        }
    }

}
