package com.example.mobiledevslb1.viewmodels


import android.app.Application
import android.util.Log
import androidx.lifecycle.viewModelScope
import com.example.mobiledevslb1.data.di.GetService
import com.example.mobiledevslb1.data.repository.MessageRepository
import com.example.mobiledevslb1.domain.model.Message
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class NewsViewModel(application: Application) : ViewModelLogged(application) {

    private val repository: MessageRepository = GetService.messageRepository(application)
    val messages: Flow<List<Message>> = repository.messages


    fun onLikeClicked(message: Message) {
        viewModelScope.launch {
            Log.d("toggleLike", "Клик прочитан ${message}")
            repository.toggleLike(message)
        }
    }

    fun loadMessages() {
        viewModelScope.launch {
            repository.refreshMessages()
        }
    }
}
