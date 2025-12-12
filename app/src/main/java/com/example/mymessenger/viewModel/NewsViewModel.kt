package com.example.mymessenger.viewModel

import android.app.Application
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.mymessenger.MessageRepository
import com.example.mymessenger.Message
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.M)
class NewsViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = MessageRepository(application)

    private val _messages = MutableLiveData<List<Message>>()
    val messages: LiveData<List<Message>> = _messages

    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean> = _isLoading

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> = _error

    init {
        Log.d("NewsViewModel", "ViewModel создан")
        loadMessages()
        refreshMessages()
    }

    @RequiresApi(Build.VERSION_CODES.M)
    fun loadMessages() {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null

            try {
                repository.getMessages().collect { messagesList ->
                    _messages.value = messagesList
                    _isLoading.value = false
                    Log.d("NewsViewModel", "Загружено ${messagesList.size} сообщений")
                }
            } catch (e: Exception) {
                _error.value = "Ошибка загрузки: ${e.message}"
                _isLoading.value = false
                Log.e("NewsViewModel", "Ошибка: ${e.message}")
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    fun refreshMessages() {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null

            try {
                repository.refreshMessages()
                _isLoading.value = false
                Log.d("NewsViewModel", "Данные обновлены вручную")
            } catch (e: Exception) {
                _error.value = "Ошибка обновления: ${e.message}"
                _isLoading.value = false
                Log.e("NewsViewModel", "Ошибка обновления: ${e.message}")
            }
        }
    }

    fun deleteMessage(message: Message) {
        viewModelScope.launch {
            try {
                repository.deleteMessage(message)
                Log.d("NewsViewModel", "Сообщение удалено: ${message.id}")
            } catch (e: Exception) {
                _error.value = "Ошибка удаления: ${e.message}"
                Log.e("NewsViewModel", "Ошибка удаления: ${e.message}")
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        Log.d("NewsViewModel", "ViewModel уничтожен")
    }
}