package com.example.messenger.data.mappers

import com.example.messenger.data.dto.NewsData
import com.example.messenger.data.entities.NewsEntity

fun NewsData.toEntity() = NewsEntity(
    id = id ?: 0,
    author = author ?: null,
    title = title,
    date = date,
    description = description,
    imageURL = imageURL,
    contentURL = contentURL
)

fun NewsEntity.toDto() = NewsData(
    id = id,
    author = author,
    title = title,
    date = date,
    description = description,
    imageURL = imageURL,
    contentURL = contentURL
)
