package com.dak0ta.learnity.core.network.data.mapper

import com.dak0ta.learnity.core.domain.Quote
import com.dak0ta.learnity.core.network.data.api.dto.QuoteDto

internal fun QuoteDto.toDomain() = Quote(
    id = id,
    quote = quote,
    author = author,
    isLiked = false,
)
