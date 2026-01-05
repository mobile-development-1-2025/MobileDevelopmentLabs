package com.example.messenger.repository

import android.util.Log
import com.example.messenger.data.dao.NewsDao
import com.example.messenger.data.dto.NewsData
import com.example.messenger.data.mappers.toDto
import com.example.messenger.data.mappers.toEntity
import com.example.messenger.httpClient.NewsApi


class NewsRepository(private val api: NewsApi, private val dao: NewsDao) {
    private var tag: String = "News Repo"

    suspend fun refreshFromNetwork(query: String, lang: String = "ru", deleteOld: Boolean = false): List<NewsData> {
        val response = api.getMainPageNews(query, lang)
        val news = response.news

        if (deleteOld) dao.deleteAllNews()

        saveNews(news)
        Log.d(tag, "Successfully saved updated news!")
        return news
    }

    suspend fun loadFromDB(): List<NewsData> {
        val entities = dao.getAllNews()
        return entities.map { it.toDto() }
    }

    private suspend fun saveNews(news: List<NewsData>) {
        dao.addNews(news.map { it.toEntity() })
    }
}
