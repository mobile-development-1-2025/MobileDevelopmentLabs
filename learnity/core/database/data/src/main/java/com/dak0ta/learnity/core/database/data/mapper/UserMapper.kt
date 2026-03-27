package com.dak0ta.learnity.core.database.data.mapper

import com.dak0ta.learnity.core.database.data.entity.UserEntity
import com.dak0ta.learnity.core.domain.User

internal fun UserEntity.toDomain() = User(
    id = id,
    email = email,
    username = username,
    firstName = firstName,
    lastName = lastName,
    gender = gender,
    image = image,
    isLocallyEdited = isLocallyEdited,
)

internal fun User.toEntity() = UserEntity(
    id = id,
    email = email,
    username = username,
    firstName = firstName,
    lastName = lastName,
    gender = gender,
    image = image,
    isLocallyEdited = isLocallyEdited,
)
