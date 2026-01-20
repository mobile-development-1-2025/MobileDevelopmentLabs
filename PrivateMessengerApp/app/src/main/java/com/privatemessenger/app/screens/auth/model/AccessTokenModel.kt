package com.privatemessenger.app.screens.auth.model

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize

@Keep
@Parcelize
data class AccessTokenModel(
    val id: Int,
    val username: String,
) : Parcelable
