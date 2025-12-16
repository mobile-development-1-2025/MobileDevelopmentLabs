package com.example.messengerapp_eliza.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.messengerapp_eliza.data.NewsRepository
import kotlinx.coroutines.launch

class NewsViewModel(private val repository: NewsRepository) : ViewModel() {

    val news = repository.newsFlow

    private val _isLoading = MutableLiveData<Boolean>(false)
    val isLoading: LiveData<Boolean> get() = _isLoading

    private val _error = MutableLiveData<String?>(null)
    val error: LiveData<String?> get() = _error

    init {
        Log.d("NewsViewModel", "Created")
        refreshNews(showLoading = false)  // Первая загрузка без индикатора
    }

    fun refreshNews(showLoading: Boolean = true) {
        if (showLoading) _isLoading.value = true
        _error.value = null

        viewModelScope.launch {
            try {
                repository.refreshNews()
                Log.d("NewsViewModel", "News refreshed successfully")
            } catch (e: Exception) {
                Log.e("NewsViewModel", "Error refreshing news", e)
                _error.value = "Не удалось обновить новости. Проверьте интернет."
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun clearError() {
        _error.value = null
    }

    override fun onCleared() {
        super.onCleared()
        Log.d("NewsViewModel", "Cleared")
    }
}