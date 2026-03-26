package com.example.lab1.ui.feed

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.lab1.data.local.AppDatabase
import com.example.lab1.data.remote.RetrofitInstance
import com.example.lab1.data.repository.MessageRepository
import kotlinx.coroutines.launch

class FeedViewModel(app: Application) : AndroidViewModel(app) {

    private val db = AppDatabase.get(app)
    private val repository = MessageRepository(
        app,
        db.messageDao(),
        RetrofitInstance.api
    )

    val messages = repository.getMessages()

    fun refresh() {
        viewModelScope.launch {
            repository.refresh()
        }
    }
}
