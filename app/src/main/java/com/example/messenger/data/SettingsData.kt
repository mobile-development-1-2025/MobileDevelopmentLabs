package com.example.messenger.data

import com.example.messenger.ui.ThemeManager
import kotlinx.serialization.Serializable

@Serializable
data class SettingsData (
    val theme: String = ThemeManager.THEME_SYSTEM,
    val notificationsEnabled: Boolean = true,
)
