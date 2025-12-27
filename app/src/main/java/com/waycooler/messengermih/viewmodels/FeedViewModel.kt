package com.waycooler.messengermih.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.waycooler.messengermih.data.local.MessageEntity
import com.waycooler.messengermih.data.repository.MessageRepository
import kotlinx.coroutines.launch

class FeedViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = MessageRepository(application)

    private val _messages = MutableLiveData<List<MessageEntity>>()
    val messages: LiveData<List<MessageEntity>> = _messages

    init {
        Log.d("FeedViewModel", "init")
        loadMessages()
    }

    override fun onCleared() {
        super.onCleared()
        Log.d("FeedViewModel", "onCleared")
    }

    fun loadMessages() {
        viewModelScope.launch {
            _messages.value = repository.getMessages()
        }
        Log.d("loadMessages", "Сообщения загружены")
    }
}
