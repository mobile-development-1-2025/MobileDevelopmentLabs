package com.example.messengerlab1

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.example.messengerlab1.data.MessageRepository
import com.example.messengerlab1.data.db.AppDatabase
import com.example.messengerlab1.data.db.MessageEntity
import kotlinx.coroutines.launch

class FeedViewModel(app: Application) : AndroidViewModel(app) {

    private val repo = MessageRepository(
        AppDatabase.get(app).messageDao()
    )

    private val _messages = MutableLiveData<List<MessageEntity>>(emptyList())
    val messages: LiveData<List<MessageEntity>> = _messages

    private val _loading = MutableLiveData(false)
    val loading: LiveData<Boolean> = _loading

    init {
        Log.d("VM", "FeedViewModel init")
        load(forceRefresh = false)
    }

    fun refresh() = load(forceRefresh = true)

    fun load(forceRefresh: Boolean) {
        viewModelScope.launch {
            _loading.value = true
            _messages.value = repo.getMessages(forceRefresh)
            _loading.value = false
        }
    }

    override fun onCleared() {
        super.onCleared()
        Log.d("VM", "FeedViewModel onCleared")
    }

    fun toggleLike(item: MessageEntity) {
        viewModelScope.launch {
            repo.setLiked(item.id, !item.liked)
            _messages.value = repo.getMessages(forceRefresh = false)
        }
    }
}