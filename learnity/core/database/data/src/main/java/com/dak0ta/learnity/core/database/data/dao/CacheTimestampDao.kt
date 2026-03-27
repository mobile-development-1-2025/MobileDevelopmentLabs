package com.dak0ta.learnity.core.database.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.dak0ta.learnity.core.database.data.entity.CacheTimestampEntity

@Dao
internal interface CacheTimestampDao {

    @Query("SELECT cache_key FROM cache_timestamps WHERE timestamp >= :since")
    suspend fun selectKeysWithNewerTimestamp(since: Long): List<String>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrReplace(entity: CacheTimestampEntity)
}
