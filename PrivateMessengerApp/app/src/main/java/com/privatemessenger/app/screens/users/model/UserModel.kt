package com.privatemessenger.app.screens.users.model

import android.os.Parcelable
import androidx.annotation.Keep
import com.privatemessenger.app.screens.users.dto.UserDto
import kotlinx.parcelize.Parcelize

@Keep
@Parcelize
data class UserModel(
    val userId: Int,
    val username: String,
    val publicKey: String
) : Parcelable

fun UserDto.toModel() = UserModel(
    userId = userId,
    username = username,
    publicKey = publicKey
)