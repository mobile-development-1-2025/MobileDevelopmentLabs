package com.example.messenger.data.dto
import com.google.gson.annotations.SerializedName

data class NewsData (
    val id: Int?,

    val author: String?,

    val title: String,

    @SerializedName("publishedAt")
    val date: String,

    val description: String,

    @SerializedName("urlToImage")
    val imageURL: String,

    @SerializedName("url")
    val contentURL: String
)

data class NewsResponse (
    @SerializedName("articles")
    val news: List<NewsData>
)