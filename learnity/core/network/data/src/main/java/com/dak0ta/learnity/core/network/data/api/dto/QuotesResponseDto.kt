package com.dak0ta.learnity.core.network.data.api.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
internal data class QuotesResponseDto(
    @param:Json(name = "quotes") val quotes: List<QuoteDto>,
    @param:Json(name = "total") val total: Int,
    @param:Json(name = "skip") val skip: Int,
    @param:Json(name = "limit") val limit: Int,
)
