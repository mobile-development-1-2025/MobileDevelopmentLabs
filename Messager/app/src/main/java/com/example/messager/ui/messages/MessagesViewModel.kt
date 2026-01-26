package com.example.messager.ui.messages

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.messager.data.MessageRepository
import com.example.messager.data.api.RetrofitClient
import com.example.messager.data.db.AppDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MessagesViewModel(application: Application) : AndroidViewModel(application) {

    private val db by lazy { AppDatabase.getInstance(application) }
    private val repo by lazy { MessageRepository(RetrofitClient.api, db.messageDao()) }

    val messages = repo.messages.asLiveData()

    fun refresh() = viewModelScope.launch(Dispatchers.IO) {
        try {
            repo.refresh()
            Log.d("Messages", "Refresh succeeded")
        } catch (e: Exception) {
            Log.e("Messages", "Refresh failed", e)
        }
    }

    fun like(id: Int, liked: Boolean) = viewModelScope.launch(Dispatchers.IO) {
        repo.like(id, liked)
    }
}