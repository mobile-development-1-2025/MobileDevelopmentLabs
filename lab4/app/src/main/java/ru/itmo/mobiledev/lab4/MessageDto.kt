package ru.itmo.mobiledev.lab4

data class MessageDto(
    val id: Int,
    val title: String,
    val body: String,
    val userId: Int
)
