package com.example.vsemk.ui.feed

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.vsemk.data.local.MessageEntity
import com.example.vsemk.data.repository.MessageRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FeedViewModel(application: Application) : AndroidViewModel(application) {

    companion object {
        private const val TAG = "FeedViewModel"
    }

    private val repository = MessageRepository(application)

    private val _messages = MutableLiveData<List<MessageEntity>>()
    val messages: LiveData<List<MessageEntity>> = _messages

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> = _error

    init {
        Log.d(TAG, "init: ViewModel создана")
        observeMessages()
        viewModelScope.launch {
            kotlinx.coroutines.delay(100)
            loadMessages()
        }
    }

    private fun observeMessages() {
        viewModelScope.launch {
            try {
                repository.getAllMessages()
                    .flowOn(Dispatchers.IO)
                    .catch { e ->
                        Log.e(TAG, "Ошибка при наблюдении за сообщениями", e)
                        _error.value = "Ошибка при загрузке сообщений: ${e.message}"
                        _isLoading.value = false
                    }
                    .collect { messageList ->
                        _messages.value = messageList
                        _isLoading.value = false
                        Log.d(TAG, "Получено сообщений: ${messageList.size}")
                    }
            } catch (e: Exception) {
                Log.e(TAG, "Критическая ошибка при наблюдении за сообщениями", e)
                _error.value = "Ошибка при загрузке сообщений: ${e.message}"
                _isLoading.value = false
            }
        }
    }

    fun loadMessages() {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                _error.value = null
                withContext(Dispatchers.IO) {
                    repository.loadMessages()
                }
            } catch (e: Exception) {
                Log.e(TAG, "Ошибка при загрузке сообщений", e)
                _error.value = "Ошибка при загрузке сообщений: ${e.message}"
                _isLoading.value = false
            }
        }
    }

    fun refreshMessages() {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                _error.value = null
                withContext(Dispatchers.IO) {
                    repository.refreshMessages()
                }
                Log.d(TAG, "Сообщения обновлены")
            } catch (e: Exception) {
                Log.e(TAG, "Ошибка при обновлении сообщений", e)
                _error.value = "Ошибка при обновлении сообщений: ${e.message}"
                _isLoading.value = false
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        Log.d(TAG, "onCleared: ViewModel уничтожена")
    }
}

