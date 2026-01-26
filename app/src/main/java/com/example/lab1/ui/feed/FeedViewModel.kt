package com.example.lab1.ui.feed

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
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
    private val _errorMessage = MutableLiveData<String?>()
    val errorMessage: LiveData<String?> = _errorMessage

    fun toggleLike(id: Int) {
        viewModelScope.launch {
            repository.toggleLike(id)
        }
    }


    fun refresh() {
        viewModelScope.launch {
            try {
                repository.refresh()
                _errorMessage.value = null // очистить прошлые ошибки
            } catch (e: Exception) {
                e.printStackTrace()
                _errorMessage.value = "Ошибка обновления. Проверьте интернет."
            }
        }

    }
}
