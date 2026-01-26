package com.example.lab1.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.example.lab1.data.MessageRepository
import com.example.lab1.data.local.MessageEntity
import kotlinx.coroutines.launch

class NewsViewModel(application: Application) : AndroidViewModel(application) {

    private val repo = MessageRepository(application.applicationContext)

    private val _messages = MutableLiveData<List<MessageEntity>>(emptyList())
    val messages: LiveData<List<MessageEntity>> = _messages

    private val _loading = MutableLiveData(false)
    val loading: LiveData<Boolean> = _loading

    private val _syncEvent = MutableLiveData<Boolean>()
    val syncEvent: LiveData<Boolean> = _syncEvent

    fun refreshFromNetwork() {
        viewModelScope.launch {
            _loading.value = true
            val (list, isNew) = repo.fetchAndSaveWithFlag()
            _messages.value = list
            _syncEvent.value = isNew
            _loading.value = false
        }
    }

    fun loadFromDb() {
        viewModelScope.launch {
            _loading.value = true
            _messages.value = repo.getFromDb()
            _loading.value = false
        }
    }

    fun toggleLike(message: MessageEntity) {
        viewModelScope.launch {
            repo.toggleLike(message)
            _messages.value = repo.getFromDb()
        }
    }
}