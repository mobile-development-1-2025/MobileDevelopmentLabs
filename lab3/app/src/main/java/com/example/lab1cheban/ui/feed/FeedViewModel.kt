package com.example.lab1cheban.ui.feed

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.work.*
import com.example.lab1cheban.data.AppDatabase
import com.example.lab1cheban.data.Message
import com.example.lab1cheban.data.MessageRepository
import com.example.lab1cheban.data.RetrofitInstance
import com.example.lab1cheban.worker.MessageSyncWorker
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

class FeedViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = MessageRepository(
        RetrofitInstance.api,
        AppDatabase.getDatabase(application)
    )

    private val _messages = MutableStateFlow<List<Message>>(emptyList())
    val messages: StateFlow<List<Message>> get() = _messages

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> get() = _isLoading

    private var currentPage = 1
    private val limit = 5

    init {
        setupPeriodicSync()
    }

    private fun setupPeriodicSync() {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val syncRequest = PeriodicWorkRequestBuilder<MessageSyncWorker>(
            15, TimeUnit.MINUTES
        )
            .setConstraints(constraints)
            .setBackoffCriteria(
                BackoffPolicy.LINEAR,
                WorkRequest.MIN_BACKOFF_MILLIS,
                TimeUnit.MILLISECONDS
            )
            .build()

        WorkManager.getInstance(getApplication()).enqueueUniquePeriodicWork(
            "message_sync",
            ExistingPeriodicWorkPolicy.KEEP,
            syncRequest
        )
    }

    fun loadPage(page: Int, showNotification: Boolean = false) {
        viewModelScope.launch {
            if (showNotification) {
                _isLoading.value = true
            }
            val data = repository.fetchMessages(page, limit)
            _messages.value = data
            currentPage = page
            if (showNotification) {
                _isLoading.value = false
            }
        }
    }

    fun nextPage() {
        loadPage(currentPage + 1)
    }

    fun prevPage() {
        if (currentPage > 1) {
            loadPage(currentPage - 1)
        }
    }

    fun refresh() {
        loadPage(1, showNotification = true)
    }

    fun toggleLike(messageId: Int) {
    }

    override fun onCleared() {
        super.onCleared()
    }
}