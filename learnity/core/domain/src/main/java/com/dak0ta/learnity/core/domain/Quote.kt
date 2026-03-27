package com.dak0ta.learnity.core.domain

data class Quote(
    val id: Int,
    val quote: String,
    val author: String,
    val isLiked: Boolean,
)
