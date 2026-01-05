package com.example.messenger.data.dto

import com.example.messenger.ui.ThemeManager

data class SettingsData (
    val theme: String = ThemeManager.THEME_SYSTEM,
    val notificationsEnabled: Boolean = true,
)
