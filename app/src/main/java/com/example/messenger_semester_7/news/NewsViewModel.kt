package com.example.messenger_semester_7.news

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.messenger_semester_7.R
import com.example.messenger_semester_7.data.Message
import com.example.messenger_semester_7.data.MessageRepository
import com.example.messenger_semester_7.data.MessageServiceLocator
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class FeedUiState(
    val isLoading: Boolean = false,
    val messages: List<Message> = emptyList(),
    val isOffline: Boolean = false,
    val errorMessage: String? = null
)

class NewsViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: MessageRepository =
        MessageServiceLocator.provideRepository(application)

    private val _uiState = MutableStateFlow(FeedUiState(isLoading = false))
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
            val result = repository.refreshMessages()
            val fallbackError = getApplication<Application>().getString(R.string.news_error_default)
            val errorText = if (result.isFailure) {
                result.exceptionOrNull()?.localizedMessage?.takeIf { it.isNotBlank() }?.let { msg ->
                    "Не удалось обновить данные: $msg"
                } ?: fallbackError
            } else {
                null
            }
            _uiState.update {
                it.copy(
                    isLoading = false,
                    isOffline = result.isFailure,
                    errorMessage = errorText
                )
            }
        }
    }
}

