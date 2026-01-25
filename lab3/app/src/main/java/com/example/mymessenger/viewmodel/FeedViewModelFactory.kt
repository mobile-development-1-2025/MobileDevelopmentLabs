package com.example.mymessenger.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mymessenger.data.MessageRepository

class FeedViewModelFactory(private val repo: MessageRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FeedViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return FeedViewModel(repo) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
