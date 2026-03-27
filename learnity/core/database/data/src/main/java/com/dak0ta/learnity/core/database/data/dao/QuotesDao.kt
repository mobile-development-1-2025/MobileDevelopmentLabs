package com.dak0ta.learnity.core.database.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.dak0ta.learnity.core.database.data.entity.QuoteEntity
import kotlinx.coroutines.flow.Flow

@Dao
internal interface QuotesDao {

    @Query("SELECT * FROM quotes")
    fun getAllFlow(): Flow<List<QuoteEntity>>

    @Query("SELECT * FROM quotes")
    suspend fun getAllOnce(): List<QuoteEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(quotes: List<QuoteEntity>)

    @Query("DELETE FROM quotes")
    suspend fun deleteAll()
}
