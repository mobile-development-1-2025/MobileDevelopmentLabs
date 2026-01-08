package com.example.mymessenger.viewModel

import android.app.Application
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.*
import com.example.mymessenger.data.MessageRepository
import com.example.mymessenger.model.Message
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.M)
class NewsViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = MessageRepository(application)

    private val _isRefreshing = MutableLiveData(false)
    val isRefreshing: LiveData<Boolean> = _isRefreshing

    val messages: LiveData<List<Message>> = repository.getMessages().asLiveData()

    private val _isLoading = MutableLiveData(false)

    private val _error = MutableLiveData<String?>()

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
        _isRefreshing.value = true
        viewModelScope.launch {
            try {
                repository.refreshMessages(showNotification = true)
            } catch (e: Exception) {
                Log.e("NewsViewModel", "Ошибка обновления: ${e.message}")
            } finally {
                _isRefreshing.value = false
            }
        }
    }

    fun likeMessage(messageId: Int, isLiked: Boolean) {
        viewModelScope.launch {
            repository.likeMessage(messageId, isLiked)
        }
    }

    fun schedulePeriodicSync() {
        repository.schedulePeriodicSync()
    }

    override fun onCleared() {
        super.onCleared()
        Log.d("NewsViewModel", "ViewModel уничтожен")
    }
}