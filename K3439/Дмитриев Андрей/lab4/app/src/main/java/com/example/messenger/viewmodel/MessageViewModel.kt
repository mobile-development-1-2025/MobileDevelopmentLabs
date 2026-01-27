package com.example.messenger.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.messenger.data.AppDatabase
import com.example.messenger.data.Message
import com.example.messenger.repository.MessageRepository
import com.example.messenger.utils.NetworkMonitor
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

/**
 * ViewModel для управления списком сообщений
 * Реализует паттерн MVVM
 */
class MessageViewModel(application: Application) : AndroidViewModel(application) {
    
    private val TAG = "MessageViewModel"
    
    private val repository: MessageRepository
    private val networkMonitor: NetworkMonitor
    
    // LiveData для списка сообщений (реактивное обновление из Room)
    val messages: LiveData<List<Message>>
    
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading
    
    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> = _error
    
    private val _isOnline = MutableLiveData<Boolean>()
    val isOnline: LiveData<Boolean> = _isOnline
    
    private val _syncMessage = MutableLiveData<String?>()
    val syncMessage: LiveData<String?> = _syncMessage

    init {
        Log.d(TAG, "MessageViewModel инициализирован")
        
        val database = AppDatabase.getDatabase(application)
        repository = MessageRepository(database.messageDao())
        networkMonitor = NetworkMonitor(application)
        
        // Подписка на реактивные обновления из базы данных
        messages = repository.messagesFlow.asLiveData()
        
        // Мониторинг состояния сети
        observeNetworkState()
        
        // Начальная загрузка
        loadMessages()
    }

    /**
     * Отслеживание состояния сети
     */
    private fun observeNetworkState() {
        viewModelScope.launch {
            networkMonitor.networkState.collectLatest { state ->
                when (state) {
                    is NetworkMonitor.NetworkState.Connected -> {
                        Log.d(TAG, "Сеть подключена")
                        _isOnline.value = true
                        _syncMessage.value = "Подключение восстановлено"
                    }
                    is NetworkMonitor.NetworkState.Disconnected -> {
                        Log.d(TAG, "Сеть отключена")
                        _isOnline.value = false
                        _syncMessage.value = "Нет подключения к сети"
                    }
                }
            }
        }
    }

    /**
     * Загрузка сообщений
     */
    fun loadMessages(forceRefresh: Boolean = false) {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            
            val result = repository.getMessages(forceRefresh)
            
            result.onSuccess { messageList ->
                Log.d(TAG, "Сообщения загружены: ${messageList.size}")
                if (forceRefresh) {
                    _syncMessage.value = "Данные обновлены"
                }
            }.onFailure { exception ->
                _error.value = exception.message ?: "Неизвестная ошибка"
                Log.e(TAG, "Ошибка загрузки сообщений: ${exception.message}")
            }
            
            _isLoading.value = false
        }
    }

    /**
     * Обновление сообщений по запросу пользователя
     */
    fun refreshMessages() {
        Log.d(TAG, "Обновление сообщений по запросу пользователя")
        loadMessages(forceRefresh = true)
    }

    /**
     * Переключение лайка на сообщении
     */
    fun toggleLike(message: Message) {
        viewModelScope.launch {
            Log.d(TAG, "Переключение лайка для сообщения ${message.id}")
            repository.toggleLike(message.id)
        }
    }

    /**
     * Очистка сообщения синхронизации
     */
    fun clearSyncMessage() {
        _syncMessage.value = null
    }

    override fun onCleared() {
        super.onCleared()
        Log.d(TAG, "MessageViewModel уничтожен")
    }
}
