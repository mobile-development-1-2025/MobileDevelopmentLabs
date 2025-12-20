package com.example.lab1.feed

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.lab1.di.ServiceLocator
import com.example.lab1.domain.model.Message
import com.example.lab1.domain.repository.MessageRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.IOException

data class FeedUiState(
    val messages: List<Message> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)

class FeedViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: MessageRepository = ServiceLocator.provideMessageRepository(application)

    private val _uiState = MutableStateFlow(FeedUiState())
    val uiState: StateFlow<FeedUiState> = _uiState.asStateFlow()

    init {
        observeMessages()
        refreshMessages()
    }

    private fun observeMessages() {
        viewModelScope.launch {
            repository.messages.collect { messages ->
                _uiState.update { it.copy(messages = messages) }
            }
        }
    }

    fun refreshMessages() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }
            try {
                repository.refreshMessages()
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

    private fun offlineMessage(isCacheEmpty: Boolean, throwable: Throwable): String {
        return if (isCacheEmpty) {
            "Нет сети и нет сохранённых данных: ${throwable.localizedMessage}"
        } else {
            "Не удалось обновить. Показаны сохранённые данные."
        }
    }
}

