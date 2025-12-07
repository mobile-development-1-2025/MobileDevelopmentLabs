package com.example.lab_1.ui.feed

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lab_1.data.repository.MessageRepository
import com.example.lab_1.domain.model.Message
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

data class FeedUiState(
    val isLoading: Boolean = false,
    val messages: List<Message> = emptyList(),
    val error: String? = null
)

class FeedViewModel(
    private val repository: MessageRepository
) : ViewModel() {

    private val _state = MutableStateFlow(FeedUiState())
    val state: StateFlow<FeedUiState> = _state

    init {
        Log.i("FeedViewModel", "init")
        observeMessages()
        refresh() // первая загрузка с сервера
    }

    private fun observeMessages() {
        viewModelScope.launch {
            repository.messagesFlow
                .catch { e ->
                    Log.e("FeedViewModel", "DB flow error", e)
                    _state.value = _state.value.copy(error = e.message)
                }
                .collectLatest { list ->
                    _state.value = _state.value.copy(messages = list)
                }
        }
    }

    fun refresh() {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true, error = null)

            val result = repository.refreshMessages()

            _state.value = _state.value.copy(isLoading = false)

            result.exceptionOrNull()?.let { e ->
                _state.value = _state.value.copy(error = e.message)
            }
        }
    }

    override fun onCleared() {
        Log.i("FeedViewModel", "onCleared")
        super.onCleared()
    }
}
