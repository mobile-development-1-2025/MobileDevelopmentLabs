package com.example.messenger.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.messenger.data.dto.NewsData
import com.example.messenger.db.MessengerDatabase
import com.example.messenger.repository.NewsRepository
import com.example.messenger.httpClients.NewsClient
import kotlinx.coroutines.launch


class NewsViewModel(application: Application): AndroidViewModel(application) {
    companion object {
        private const val tag: String = "NewsVM"
    }

    private var repository: NewsRepository
    private val _news = MutableLiveData<List<NewsData>>(emptyList())

    private val query = MutableLiveData("")
    val news: LiveData<List<NewsData>> = _news

    init {
        Log.d(tag, "Init News ViewModel")
        val db = MessengerDatabase.getInstance(application)
        repository = NewsRepository(NewsClient.api, db.NewsDao())
        loadNews()
    }

    fun loadNews() {
        viewModelScope.launch {
            val fromDbList = repository.loadFromDB()
            _news.value = fromDbList

            launch {
                try {
                    _news.value = repository.refreshFromNetwork(query.value!!)
                } catch (e: Exception) {
                    Log.e(tag, "Failed to load news data from network, skip update...", e)
                }
            }

        }
    }

    fun setQuery(text: String) {
        if (text == query.value) return
        query.value = text
        loadNews()
    }

    override fun onCleared() {
        Log.d(tag, "onCleared: FeedViewModel has been cleared")
        super.onCleared()
    }
}