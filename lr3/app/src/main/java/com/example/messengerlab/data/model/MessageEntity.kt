package com.example.messengerlab.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "messages")
data class MessageEntity(
    @PrimaryKey
    val id: Int,

    @SerializedName("title")
    val author: String,

    @SerializedName("body")
    val text: String,

    val timestamp: String = "12:00"
)