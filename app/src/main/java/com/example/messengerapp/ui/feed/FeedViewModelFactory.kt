package com.example.messengerapp.ui.feed

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.messengerapp.data.MessageRepository

class FeedViewModelFactory(
    private val repo: MessageRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FeedViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return FeedViewModel(repo) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
