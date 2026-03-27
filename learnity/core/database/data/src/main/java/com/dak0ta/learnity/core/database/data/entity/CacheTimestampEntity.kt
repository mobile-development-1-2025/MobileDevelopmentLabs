package com.dak0ta.learnity.core.database.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cache_timestamps")
data class CacheTimestampEntity(
    @PrimaryKey
    @ColumnInfo(name = "cache_key")
    val key: String,
    val timestamp: Long,
)
