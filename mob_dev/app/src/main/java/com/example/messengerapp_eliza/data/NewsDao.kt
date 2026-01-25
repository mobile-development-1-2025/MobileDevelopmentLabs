package com.example.messengerapp_eliza.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface NewsDao {
    @Query("SELECT * FROM news ORDER BY id DESC")
    fun getAllNews(): Flow<List<NewsItem>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(news: List<NewsItem>)

    @Query("DELETE FROM news")
    suspend fun deleteAll()

    @Query("UPDATE news SET isLiked = :liked WHERE id = :id")
    suspend fun updateLike(id: Int, liked: Boolean)

}