package com.example.messengerapp_eliza.data

import android.content.Context
import com.example.messengerapp_eliza.data.remote.RetrofitClient
import com.example.messengerapp_eliza.utils.NetworkUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException

class NewsRepository(
    private val appContext: Context,
    private val newsDao: NewsDao
) {

    val newsFlow = newsDao.getAllNews()

    private var currentPage = 0
    private val pageSize = 20

    private var usersCache: Map<Int, String>? = null

    private suspend fun loadUsers(): Map<Int, String> {
        return usersCache ?: run {
            val response = RetrofitClient.apiService.getUsers()
            val map = response.users.associate { it.id to it.username }
            usersCache = map
            map
        }
    }

    suspend fun refreshNews() {
        if (!NetworkUtils.isOnline(appContext)) {
            throw Exception("Нет интернета")
        }

        withContext(Dispatchers.IO) {
            val users = loadUsers()
            val skip = currentPage * pageSize

            try {
                val response = RetrofitClient.apiService.getNews(
                    limit = pageSize,
                    skip = skip
                )

                val newsList = response.posts.map {
                    NewsItem(
                        id = it.id,
                        title = it.title,
                        body = it.body,
                        authorName = users[it.userId] ?: "Unknown",
                        isLiked = false
                    )
                }

                newsDao.insertAll(newsList)
                currentPage++

            } catch (e: HttpException) {
                throw Exception("Ошибка сервера: ${e.code()}")
            }
        }
    }

    suspend fun refreshNews(forceRefresh: Boolean = false) {
        if (forceRefresh) {
            currentPage = 0
            newsDao.deleteAll()
        }

        if (!NetworkUtils.isOnline(appContext)) {
            throw Exception("Нет интернета")
        }

        withContext(Dispatchers.IO) {
            val users = loadUsers()
            val skip = currentPage * pageSize

            val response = RetrofitClient.apiService.getNews(
                limit = pageSize,
                skip = skip
            )

            val newsList = response.posts.map {
                NewsItem(
                    id = it.id,
                    title = it.title,
                    body = it.body,
                    authorName = users[it.userId] ?: "Unknown",
                    isLiked = false
                )
            }

            newsDao.insertAll(newsList)
            currentPage++
        }
    }


    suspend fun updateLike(id: Int, liked: Boolean) {
        newsDao.updateLike(id, liked)
    }
}
