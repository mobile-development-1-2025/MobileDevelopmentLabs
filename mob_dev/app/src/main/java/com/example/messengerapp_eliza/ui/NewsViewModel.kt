package com.example.messengerapp_eliza.ui

import android.util.Log
import androidx.lifecycle.*
import com.example.messengerapp_eliza.data.NewsItem
import com.example.messengerapp_eliza.data.NewsRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class NewsViewModel(
    private val repository: NewsRepository
) : ViewModel() {

    val news = repository.newsFlow
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())

    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean> = _isLoading

    private val _error = MutableLiveData<String?>(null)
    val error: LiveData<String?> = _error

    init {
        refreshNews(showLoading = true)
    }

    fun refreshNews(showLoading: Boolean = true) {
        if (showLoading) _isLoading.value = true
        _error.value = null

        viewModelScope.launch {
            try {
                repository.refreshNews(forceRefresh = true)
            } catch (e: Exception) {
                _error.value = e.message
                Log.e("NewsViewModel", "refresh failed", e)
            } finally {
                _isLoading.value = false
            }
        }
    }


    fun toggleLike(item: NewsItem) {
        viewModelScope.launch {
            repository.updateLike(item.id, !item.isLiked)
        }
    }
}
