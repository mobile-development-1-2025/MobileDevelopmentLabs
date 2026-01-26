package com.example.messenger.data.mappers

import com.example.messenger.data.dto.MessageData
import com.example.messenger.data.dto.MessageResponseData
import com.example.messenger.data.entities.MessagesEntity

const val avatarURLBase = "https://i.pravatar.cc/"
const val baseImageSize = 250

fun buildAvatarUrl(senderId: Int): String {
    return "$avatarURLBase/$baseImageSize?u=$senderId"
}

fun MessageResponseData.toEntity(senderName: String?) = MessagesEntity(
    id = id ?: 0,
    senderName = senderName,
    senderAvatarURL = buildAvatarUrl(senderId),
    text = text,
)

fun MessageData.toEntity() = MessagesEntity(
    id = id,
    senderName = username,
    text = text,
    senderAvatarURL = avatarURL,
    liked = liked,
)

fun MessagesEntity.toDto() = MessageData(
    id = id,
    text = text,
    username = senderName,
    avatarURL = senderAvatarURL,
    liked = liked,
)
