package com.waycooler.messengermih.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.waycooler.messengermih.data.local.AppDatabase
import com.waycooler.messengermih.data.local.MessageEntity
import com.waycooler.messengermih.data.notification.NotificationHelper
import com.waycooler.messengermih.data.repository.MessageRepository
import kotlinx.coroutines.launch

class FeedViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = MessageRepository(application)
    private val context = application.applicationContext

    private val _messages = MutableLiveData<List<MessageEntity>>()
    val messages: LiveData<List<MessageEntity>> = _messages

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    init {
        loadMessages()
    }

    fun loadMessages() {
        _isLoading.value = true
        viewModelScope.launch {
            _messages.value = repository.getMessages()
            _isLoading.value = false
        }
    }

    fun refreshMessages() {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                repository.refreshMessages()

                _messages.value = repository.getLocalMessages()

                showNotification()
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun onLikeClicked(messageId: Int) {
        viewModelScope.launch {
            repository.toggleLike(messageId)
            _messages.value = repository.getLocalMessages()
        }
    }

    fun clearDatabase() {
        viewModelScope.launch {
            val db = AppDatabase.getInstance(getApplication())
            db.messageDao().clearMessages()
            _messages.value = emptyList()
            Log.d("FeedViewModel", "База данных очищена")
        }
    }

    private fun showNotification() {
        NotificationHelper.createChannel(context)
        NotificationHelper.showNotification(context)
    }
}