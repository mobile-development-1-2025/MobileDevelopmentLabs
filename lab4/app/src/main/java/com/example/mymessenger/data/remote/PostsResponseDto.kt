package com.example.mymessenger.data.remote

data class PostsResponseDto(
    val posts: List<PostDto>
)

data class PostDto(
    val id: Int,
    val title: String,
    val body: String,
    val userId: Int
)
