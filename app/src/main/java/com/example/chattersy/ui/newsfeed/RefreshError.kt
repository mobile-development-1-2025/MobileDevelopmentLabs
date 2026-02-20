package com.example.chattersy.ui.newsfeed

enum class RefreshErrorType {
    OFFLINE,
    FORCE_OFFLINE,
    TIMEOUT,
    GENERIC
}

data class RefreshError(
    val message: String,
    val type: RefreshErrorType
) {
    val showAsAlert: Boolean
        get() = type == RefreshErrorType.OFFLINE || type == RefreshErrorType.FORCE_OFFLINE || type == RefreshErrorType.TIMEOUT
}
