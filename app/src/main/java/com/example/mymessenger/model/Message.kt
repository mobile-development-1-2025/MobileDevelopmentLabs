package com.example.mymessenger.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.util.Date


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

    @ColumnInfo(defaultValue = "")
    @SerializedName("authorEmail")
    val authorEmail: String = "",

    @ColumnInfo(defaultValue = "")
    @SerializedName("avatarUrl")
    val avatarUrl: String = "",

    val timestamp: Long = Date().time,
    var isLiked: Boolean = false
)
