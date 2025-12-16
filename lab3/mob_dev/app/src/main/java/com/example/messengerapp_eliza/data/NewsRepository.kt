package com.example.messengerapp_eliza.data

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import com.example.messengerapp_eliza.data.remote.RetrofitClient
import com.example.messengerapp_eliza.data.remote.NewsItemApi

class NewsRepository(private val newsDao: NewsDao) {

    val newsFlow = newsDao.getAllNews()

    suspend fun refreshNews() {
        withContext(Dispatchers.IO) {
            try {
                val response = RetrofitClient.apiService.getNews()
                val newsList = response.posts.map {
                    NewsItem(it.id, it.title, it.body)
                }
                newsDao.deleteAll()
                newsDao.insertAll(newsList)
            } catch (e: Exception) {
            }
        }
    }
}