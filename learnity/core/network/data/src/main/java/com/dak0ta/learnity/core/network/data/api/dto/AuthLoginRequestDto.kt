package com.dak0ta.learnity.core.network.data.api.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
internal data class AuthLoginRequestDto(
    @param:Json(name = "username") val username: String,
    @param:Json(name = "password") val password: String,
)
