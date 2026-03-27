package com.dak0ta.learnity.core.database.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "quotes")
data class QuoteEntity(
    @PrimaryKey val id: Int,
    val quote: String,
    val author: String,
    val isLiked: Boolean,
)
