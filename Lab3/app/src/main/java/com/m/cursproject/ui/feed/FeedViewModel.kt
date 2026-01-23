package com.m.cursproject.ui.feed

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.m.cursproject.data.model.Message
import com.m.cursproject.data.repository.MessageRepository
import kotlinx.coroutines.launch

class FeedViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = MessageRepository.getInstance(application)

    val messages: LiveData<List<Message>> = repository.allMessages

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _errorMessage = MutableLiveData<String?>()
    val errorMessage: LiveData<String?> = _errorMessage

    init {
        Log.d(TAG, "FeedViewModel: created")
        loadMessages()
    }

    fun loadMessages() {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null

            val result = repository.refreshMessages()

            result.onSuccess {
                Log.d(TAG, "Messages loaded successfully")
            }.onFailure { error ->
                Log.e(TAG, "Failed to load messages: ${error.message}")
                _errorMessage.value = "Не удалось загрузить сообщения. Показаны данные из кэша."
            }

            _isLoading.value = false
        }
    }

    override fun onCleared() {
        super.onCleared()
        Log.d(TAG, "FeedViewModel: cleared")
    }

    companion object {
        private const val TAG = "FeedViewModel"
    }
}