package com.example.messengerapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.messengerapp.data.model.MessageEntity
import com.example.messengerapp.data.repository.MessageRepository
import kotlinx.coroutines.launch
import android.util.Log

class FeedViewModel(
    private val repository: MessageRepository
) : ViewModel() {

    val messages = MutableLiveData<List<MessageEntity>>()
    val loading = MutableLiveData<Boolean>(false)
    val error = MutableLiveData<String?>()

    init {
        Log.d("FeedViewModel", "ViewModel created")
        loadMessages()
    }

    fun loadMessages() {
        viewModelScope.launch {
            loading.value = true
            try {
                val result = repository.getMessages()
                messages.value = result
                error.value = null
            } catch (e: Exception) {
                error.value = e.message
            } finally {
                loading.value = false
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        Log.d("FeedViewModel", "ViewModel cleared")
    }
}