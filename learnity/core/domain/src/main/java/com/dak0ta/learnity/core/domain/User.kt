package com.dak0ta.learnity.core.domain

data class User(
    val id: Int,
    val email: String,
    val username: String,
    val firstName: String,
    val lastName: String,
    val gender: Gender,
    val image: String,
    val isLocallyEdited: Boolean,
)
