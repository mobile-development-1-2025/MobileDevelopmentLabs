package com.example.lab1.data.api

data class PostsResponseDto(
    val posts: List<PostDto>,
    val total: Int,
    val skip: Int,
    val limit: Int
)

data class PostDto(
    val id: Int,
    val title: String,
    val body: String,
    val userId: Int
)
