package com.privatemessenger.app.screens.auth.model

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize

@Keep
@Parcelize
data class AuthUiState(
    val isLoggedIn: Boolean = false,
    val isNetworkRequestActive: Boolean = false,
    val errorMessage: String = "",
    val infoMessage: String = "",
    val invalidUsername: Boolean = false,
    val invalidPassword: Boolean = false,
) : Parcelable