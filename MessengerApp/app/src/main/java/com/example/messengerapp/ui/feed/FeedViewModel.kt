package com.example.messenger.ui.feed

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

data class Message(
    val id: Int,
    val name: String,
    val email: String,
    val body: String
)

class FeedViewModel : ViewModel() {

    private val TAG = "FeedViewModel"

    private val _messages = MutableLiveData<List<Message>>()
    val messages: LiveData<List<Message>> = _messages

    private val _currentPage = MutableLiveData<Int>(1)
    val currentPage: LiveData<Int> = _currentPage

    init {
        Log.d(TAG, "ViewModel создана")
        loadSampleData()
    }

    private fun loadSampleData() {
        val sampleMessages = listOf(
            Message(1, "Пользователь 1", "user1@example.com", "Первый пост"),
            Message(2, "Пользователь 2", "user2@example.com", "Второй пост"),
            Message(3, "Пользователь 3", "user3@example.com", "Третий пост"),
            Message(4, "Пользователь 4", "user4@example.com", "Четвёртоый пост"),
            Message(5, "Пользователь 5", "user5@example.com", "Пятый пост")
        )
        _messages.value = sampleMessages
        Log.d(TAG, "Загружены тестовые данные: ${sampleMessages.size} сообщений")
    }

    fun nextPage() {
        _currentPage.value = (_currentPage.value ?: 1) + 1
        Log.d(TAG, "Переход на страницу: ${_currentPage.value}")
        loadSampleData()
    }

    fun prevPage() {
        val current = _currentPage.value ?: 1
        if (current > 1) {
            _currentPage.value = current - 1
            Log.d(TAG, "Переход на страницу: ${_currentPage.value}")
            loadSampleData()
        }
    }

    fun refresh() {
        Log.d(TAG, "Обновление страницы: ${_currentPage.value}")
        loadSampleData()
    }

    override fun onCleared() {
        super.onCleared()
        Log.d(TAG, "ViewModel очищена")
    }
}