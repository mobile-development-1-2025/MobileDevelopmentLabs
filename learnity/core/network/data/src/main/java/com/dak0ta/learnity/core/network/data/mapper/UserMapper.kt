package com.dak0ta.learnity.core.network.data.mapper

import com.dak0ta.learnity.core.domain.User
import com.dak0ta.learnity.core.network.data.api.dto.UserDto

internal fun UserDto.toDomain() = User(
    id = id,
    email = email,
    username = username,
    firstName = firstName,
    lastName = lastName,
    gender = gender,
    image = image,
    isLocallyEdited = false,
)
