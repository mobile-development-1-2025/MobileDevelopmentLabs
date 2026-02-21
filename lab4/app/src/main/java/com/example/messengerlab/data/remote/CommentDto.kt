package com.example.messengerlab.data.remote

data class CommentDto(
    val id: Int,
    val body: String,
    val postId: Int,
    val likes: Int,
    val user: UserDto
)

data class UserDto(
    val id: Int,
    val username: String,
    val fullName: String
)