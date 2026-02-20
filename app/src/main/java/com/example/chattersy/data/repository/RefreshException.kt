package com.example.chattersy.data.repository

class NoNetworkException(
    message: String = "No network available",
    val forceOffline: Boolean = false
) : Exception(message)
