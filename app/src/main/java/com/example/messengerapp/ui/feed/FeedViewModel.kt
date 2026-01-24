package com.example.messengerapp.ui.feed

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.messengerapp.data.MessageRepository
import com.example.messengerapp.data.local.MessageEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FeedViewModel(
    private val repo: MessageRepository
) : ViewModel() {

    private val _messages = MutableLiveData<List<MessageEntity>>(emptyList())
    val messages: LiveData<List<MessageEntity>> = _messages

    private val _lastUpdated = MutableLiveData("—")
    val lastUpdated: LiveData<String> = _lastUpdated

    private fun nowTime(): String =
        java.text.SimpleDateFormat("HH:mm:ss", java.util.Locale.getDefault())
            .format(java.util.Date())

    fun load() {
        viewModelScope.launch(Dispatchers.IO) {
            val data = repo.loadMessages()
            _messages.postValue(data)
            _lastUpdated.postValue(nowTime())
        }
    }

    // поскольку из апишки приходят статичные данные на любой запрос,
    // добавим поле "Последнее обновление" и докажем работоспособность кнопки "Обновить")
    fun refresh() {
        viewModelScope.launch(Dispatchers.IO) {
            val data = repo.refresh()
            _messages.postValue(data)
            _lastUpdated.postValue(nowTime())
        }
    }

    fun toggleLike(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            repo.toggleLike(id)
            _messages.postValue(repo.loadMessages())
        }
    }
}
