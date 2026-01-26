package com.example.messenger.viewmodel

import android.app.Application

import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.messenger.data.dto.MessageData
import com.example.messenger.db.MessengerDatabase
import com.example.messenger.repository.MessagesRepository
import com.example.messenger.httpClients.MessagesClient
import kotlinx.coroutines.launch


class MessagesViewModel(application: Application): AndroidViewModel(application) {
    companion object {
        private const val tag: String = "MessagesVM"
    }

    private var repository: MessagesRepository

    private val _messages = MutableLiveData<List<MessageData>>(emptyList())
    val messages : LiveData<List<MessageData>> = _messages


    init {
        Log.d(tag, "Init Messages ViewModel")
        val db = MessengerDatabase.getInstance(application)
        repository = MessagesRepository(
            MessagesClient.api,
            db.MessagesDao(),
            application.applicationContext
        )

        viewModelScope.launch {
            repository.observeMessages()
                .collect { messages ->
                    Log.d(tag, "MESSAGES = $messages")
                    _messages.value = messages
                }
        }
    }

    fun onLikeClicked(message: MessageData) {
        viewModelScope.launch {
            repository.toggleLike(message)
        }
    }

//    private fun addToMessagesList(msg: MessageData) {
//        val currentList = _messages.value.orEmpty()
//        _messages.value = currentList + msg
//    }

//    fun loadMessages() {
//        Log.d(tag, "Start messages loading...")
//
//        viewModelScope.launch {
//            val messagesFromDb = repository.loadMessagesFromDb()
//            _messages.value = messagesFromDb
//        }
//    }
//
//    fun loadNewMessage() {
//        Log.d(tag, "New message is loading...")
//
//        viewModelScope.launch {
//            try {
//                val newMessage = repository.loadMessageFromNetwork()
//                addToMessagesList(newMessage)
//            } catch (e: Exception) {
//                Log.e(tag, "Failed to load new message from network due to error: ", e)
//            }
//        }
//    }

    override fun onCleared() {
        Log.d(tag, "onCleared: MessagesViewModel has been cleared")
        super.onCleared()
    }
}
