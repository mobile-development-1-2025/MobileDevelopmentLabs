package com.example.lab1.data.remote.dto

data class CommentsResponseDto(
    val comments: List<CommentDto>,
    val total: Int? = null,
    val skip: Int? = null,
    val limit: Int? = null
)

data class CommentDto(
    val id: Long,
    val body: String,
    val postId: Long,
    val user: CommentUserDto
)

data class CommentUserDto(
    val id: Long,
    val username: String
)

