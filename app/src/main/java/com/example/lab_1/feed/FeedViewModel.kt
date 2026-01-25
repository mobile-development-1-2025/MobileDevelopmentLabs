package com.example.lab_1.ui.feed

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.lab_1.data.repository.MessageRepository
import com.example.lab_1.domain.model.Message
import com.example.lab_1.utils.NetworkUtils
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

data class FeedUiState(
    val isLoading: Boolean = false,
    val messages: List<Message> = emptyList(),
    val error: String? = null,
    val isOnline: Boolean = true,
    val toastMessage: String? = null
)

class FeedViewModel(
    application: Application,
    private val repository: MessageRepository
) : AndroidViewModel(application) {

    private val _state = MutableStateFlow(FeedUiState())
    val state: StateFlow<FeedUiState> = _state

    init {
        Log.i("FeedViewModel", "init")
        observeMessages()
        observeNetwork()
        refresh()
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

    private fun observeNetwork() {
        viewModelScope.launch {
            NetworkUtils.networkStatusFlow(getApplication()).collect { online ->
                val wasOnline = _state.value.isOnline

                _state.value = _state.value.copy(isOnline = online)

                if (!wasOnline && online) {
                    _state.value = _state.value.copy(
                        toastMessage = "Network is back. Syncing..."
                    )
                    refresh()
                }

                if (wasOnline && !online) {
                    _state.value = _state.value.copy(
                        toastMessage = "No network. Only saved messages are shown"
                    )
                }
            }
        }
    }

    fun refresh() {
        viewModelScope.launch {
            val onlineNow = NetworkUtils.isOnline(getApplication())

            _state.value = _state.value.copy(
                isLoading = true,
                error = null,
                isOnline = onlineNow
            )

            if (!onlineNow) {
                _state.value = _state.value.copy(
                    isLoading = false,
                    toastMessage = "No network. Only saved messages are shown"
                )
                return@launch
            }

            val result = repository.refreshMessages()

            _state.value = _state.value.copy(isLoading = false)

            result.exceptionOrNull()?.let { e ->
                _state.value = _state.value.copy(error = e.message)
            }
        }
    }

    fun onLikeClicked(message: Message) {
        viewModelScope.launch {
            repository.toggleLike(message.id, !message.liked)
        }
    }

    fun consumeToastMessage() {
        _state.value = _state.value.copy(toastMessage = null)
    }

    override fun onCleared() {
        Log.i("FeedViewModel", "onCleared")
        super.onCleared()
    }
}
