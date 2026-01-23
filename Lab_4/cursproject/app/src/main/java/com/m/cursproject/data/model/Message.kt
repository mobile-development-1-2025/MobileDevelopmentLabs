package com.m.cursproject.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "messages")
data class Message(
    @PrimaryKey
    @SerializedName("id")
    val id: Int,

    @SerializedName("userId")
    val userId: Int,

    @SerializedName("title")
    val title: String,

    @SerializedName("body")
    val body: String,

    val timestamp: Long = System.currentTimeMillis(),

    val isLiked: Boolean = false,
    val userName: String = "User #$userId",
    val avatarUrl: String = "https://i.pravatar.cc/150?img=$userId"
)