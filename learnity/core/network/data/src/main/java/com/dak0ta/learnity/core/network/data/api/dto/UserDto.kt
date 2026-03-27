package com.dak0ta.learnity.core.network.data.api.dto

import com.dak0ta.learnity.core.domain.Gender
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
internal data class UserDto(
    @param:Json(name = "id") val id: Int,
    @param:Json(name = "email") val email: String,
    @param:Json(name = "username") val username: String,
    @param:Json(name = "firstName") val firstName: String,
    @param:Json(name = "lastName") val lastName: String,
    @param:Json(name = "gender") val gender: Gender,
    @param:Json(name = "image") val image: String,
)
