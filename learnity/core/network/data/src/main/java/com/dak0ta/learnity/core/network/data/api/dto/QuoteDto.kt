package com.dak0ta.learnity.core.network.data.api.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
internal data class QuoteDto(
    @param:Json(name = "id") val id: Int,
    @param:Json(name = "quote") val quote: String,
    @param:Json(name = "author") val author: String,
)
