package com.dak0ta.learnity.core.database.data.mapper

import com.dak0ta.learnity.core.database.data.entity.QuoteEntity
import com.dak0ta.learnity.core.domain.Quote

internal fun QuoteEntity.toDomain() = Quote(
    id = id,
    quote = quote,
    author = author,
    isLiked = isLiked,
)

internal fun Quote.toEntity() = QuoteEntity(
    id = id,
    quote = quote,
    author = author,
    isLiked = isLiked,
)
