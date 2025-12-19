package com.example.messengerlab.data.model

import com.google.gson.annotations.SerializedName

data class PostResponse(
    @SerializedName("posts")
    val posts: List<MessageEntity>
)