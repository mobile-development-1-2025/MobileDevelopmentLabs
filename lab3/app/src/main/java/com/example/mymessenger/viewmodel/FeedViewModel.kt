package com.example.mymessenger.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mymessenger.data.MessageRepository
import com.example.mymessenger.data.local.MessageEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FeedViewModel(private val repo: MessageRepository) : ViewModel() {

    private val TAG = "FeedViewModel"

    private val _messages = MutableLiveData<List<MessageEntity>>(emptyList())
    val messages: LiveData<List<MessageEntity>> = _messages

    init {
        Log.d(TAG, "ViewModel created")
        refresh(force = false)
    }

    fun refresh(force: Boolean) {
        Log.d(TAG, "refresh(force=$force)")
        viewModelScope.launch(Dispatchers.IO) {
            val list = repo.loadMessages(forceRefresh = force)
            Log.d(TAG, "loaded size=${list.size}")
            _messages.postValue(list)
        }
    }

    override fun onCleared() {
        super.onCleared()
        Log.d(TAG, "ViewModel cleared")
    }
}
