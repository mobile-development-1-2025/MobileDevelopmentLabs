package com.example.messengerapp_eliza.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "news")
data class NewsItem(
    @PrimaryKey val id: Int,
    val title: String,
    val body: String
)