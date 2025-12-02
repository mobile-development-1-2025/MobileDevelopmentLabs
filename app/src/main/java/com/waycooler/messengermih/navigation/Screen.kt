package com.waycooler.messengermih.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screen(val route: String, val label: String, val icon: ImageVector) {
    object Feed : Screen("feed", "Лента", Icons.Default.Home)
    object Profile : Screen("profile", "Профиль", Icons.Default.AccountBox)
    object Settings : Screen("settings", "Настройки", Icons.Default.Settings)

    companion object {
        val items = listOf(Feed, Profile, Settings)
    }
}
