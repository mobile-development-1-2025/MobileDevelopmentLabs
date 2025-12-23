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

    fun refresh() {
        viewModelScope.launch {
            _loading.value = true
            val list = repo.fetchAndSave()
            _messages.value = list
            _loading.value = false
        }
    }

    fun loadFromDb() {
        viewModelScope.launch {
            _loading.value = true
            val list = repo.getFromDb()
            _messages.value = list
            _loading.value = false
        }
    }
}
