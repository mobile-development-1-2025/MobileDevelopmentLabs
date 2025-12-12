package com.example.messenger

data class UserProfile(
    var name: String,
    var username: String,
    var phoneNumber: String
)


object ProfileData {
    var user = UserProfile(
        name = "Laura Palmer",
        username = "meanwhile",
        phoneNumber = "+0 (000) 000-00-00"
    )
}