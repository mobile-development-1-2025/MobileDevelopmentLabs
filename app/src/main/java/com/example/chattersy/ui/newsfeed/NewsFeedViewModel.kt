package com.example.chattersy.ui.newsfeed

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.chattersy.data.model.Message
import com.example.chattersy.data.repository.MessageRepository
import com.example.chattersy.data.repository.NoNetworkException
import com.example.chattersy.data.repository.RefreshResult
import kotlinx.coroutines.launch
import java.net.SocketTimeoutException

class NewsFeedViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = MessageRepository(application)

    companion object {
        private const val TAG = "NewsFeedViewModel"
    }

    val messages: LiveData<List<Message>> = repository.getMessages().asLiveData()

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _showSwipeRefreshIndicator = MutableLiveData<Boolean>()
    val showSwipeRefreshIndicator: LiveData<Boolean> = _showSwipeRefreshIndicator

    private val _error = MutableLiveData<RefreshError?>()
    val error: LiveData<RefreshError?> = _error

    private val _showDataUpToDate = MutableLiveData<Boolean>()
    val showDataUpToDate: LiveData<Boolean> = _showDataUpToDate

    init {
        loadInitialMessages()
    }

    private fun loadInitialMessages() {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                repository.loadInitialMessages()
            } catch (e: Exception) {
                Log.e(TAG, "Error loading initial messages", e)
                _error.value = mapToRefreshError(e)
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun refreshMessages(fromSwipe: Boolean = false) {
        if (_isLoading.value == true) return
        viewModelScope.launch {
            try {
                _isLoading.value = true
                _showSwipeRefreshIndicator.value = fromSwipe
                _error.value = null
                _showDataUpToDate.value = false
                val result = repository.refreshMessages()
                if (result.wasUpToDate) {
                    _showDataUpToDate.value = true
                }
            } catch (e: Exception) {
                Log.e(TAG, "Error refreshing messages", e)
                _error.value = mapToRefreshError(e)
            } finally {
                _isLoading.value = false
                _showSwipeRefreshIndicator.value = false
            }
        }
    }

    private fun mapToRefreshError(e: Exception): RefreshError {
        return when {
            e is NoNetworkException -> RefreshError(
                message = "",
                type = if (e.forceOffline) RefreshErrorType.FORCE_OFFLINE else RefreshErrorType.OFFLINE
            )
            e is SocketTimeoutException || isTimeout(e) -> RefreshError(
                message = "",
                type = RefreshErrorType.TIMEOUT
            )
            else -> RefreshError(
                message = "Ошибка обновления: ${e.message ?: "неизвестная ошибка"}",
                type = RefreshErrorType.GENERIC
            )
        }
    }

    private fun isTimeout(e: Exception): Boolean {
        val msg = e.message?.lowercase() ?: ""
        return "timeout" in msg || "timed out" in msg
    }

    fun likeMessage(id: Int) {
        viewModelScope.launch {
            try {
                repository.likeMessage(id)
            } catch (e: Exception) {
                Log.e(TAG, "Error liking message", e)
            }
        }
    }

    fun clearError() {
        _error.value = null
    }

    fun clearShowDataUpToDate() {
        _showDataUpToDate.value = false
    }

    override fun onCleared() {
        super.onCleared()
        Log.d(TAG, "onCleared")
    }
}
