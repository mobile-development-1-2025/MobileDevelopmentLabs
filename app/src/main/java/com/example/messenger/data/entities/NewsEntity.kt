package com.example.messenger.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "news")
data class NewsEntity (
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val author: String?,
    val title: String,
    val date: String,
    val description: String,
    val imageURL: String,
    val contentURL: String
)
