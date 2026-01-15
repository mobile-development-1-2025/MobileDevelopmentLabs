package com.example.lab1.feed

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.lab1.data.network.NetworkMonitor
import com.example.lab1.data.notification.NotificationManager
import com.example.lab1.di.ServiceLocator
import com.example.lab1.domain.model.Message
import com.example.lab1.domain.repository.MessageRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.IOException

data class FeedUiState(
    val messages: List<Message> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val networkStatus: String? = null
)

class FeedViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: MessageRepository = ServiceLocator.provideMessageRepository(application)
    private val networkMonitor = NetworkMonitor(application)
    private val notificationManager = NotificationManager(application)

    private val _uiState = MutableStateFlow(FeedUiState())
    val uiState: StateFlow<FeedUiState> = _uiState.asStateFlow()

    init {
        observeMessages()
        observeNetworkStatus()
        refreshMessages()
    }

    private fun observeMessages() {
        viewModelScope.launch {
            repository.messages.collect { messages ->
                _uiState.update { it.copy(messages = messages) }
            }
        }
    }

    private fun observeNetworkStatus() {
        viewModelScope.launch {
            networkMonitor.isOnline.collect { isOnline ->
                _uiState.update { state ->
                    state.copy(
                        networkStatus = if (isOnline) "Онлайн" else "Офлайн"
                    )
                }
            }
        }
    }

    fun refreshMessages() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }
            try {
                val messageCount = repository.refreshMessages()
                // Показываем уведомление при успешной синхронизации
                notificationManager.showSyncSuccessNotification(messageCount)
            } catch (io: IOException) {
                _uiState.update { state ->
                    state.copy(
                        errorMessage = offlineMessage(state.messages.isEmpty(), io)
                    )
                }
            } catch (exception: Exception) {
                _uiState.update { state ->
                    state.copy(errorMessage = exception.localizedMessage ?: "Ошибка загрузки")
                }
            } finally {
                _uiState.update { it.copy(isLoading = false) }
            }
        }
    }

    fun toggleLike(messageId: Long) {
        viewModelScope.launch {
            try {
                repository.toggleLike(messageId)
                // The messages flow will automatically update
            } catch (e: Exception) {
                _uiState.update { state ->
                    state.copy(errorMessage = "Ошибка при обновлении лайка: ${e.message}")
                }
            }
        }
    }

    private fun offlineMessage(isCacheEmpty: Boolean, throwable: Throwable): String {
        return if (isCacheEmpty) {
            "Нет сети и нет сохранённых данных: ${throwable.localizedMessage}"
        } else {
            "Не удалось обновить. Показаны сохранённые данные."
        }
    }
}

