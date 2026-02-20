package com.example.messenger.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.messenger.data.entities.NewsEntity

@Dao
interface NewsDao {
    @Query("SELECT * FROM news")
    suspend fun getAllNews(): List<NewsEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addNews(messages: List<NewsEntity>)

    @Query("DELETE FROM news")
    suspend fun deleteAllNews()

    @Query("DELETE FROM news WHERE id = :id")
    suspend fun deleteNewsById(id: Int)
}
