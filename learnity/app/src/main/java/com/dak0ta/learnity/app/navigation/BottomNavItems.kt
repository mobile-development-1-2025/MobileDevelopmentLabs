package com.dak0ta.learnity.app.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import com.dak0ta.learnity.feature.home.domain.navigation.HomeDirection
import com.dak0ta.learnity.feature.profile.domain.navigation.ProfileDirection
import com.dak0ta.learnity.feature.settings.domain.SettingsDirection

val bottomNavItems = listOf(
    BottomNavItem(
        directionClass = HomeDirection::class,
        label = "Home",
        icon = Icons.Default.Home,
    ),
    BottomNavItem(
        directionClass = ProfileDirection::class,
        label = "Profile",
        icon = Icons.Default.Person,
    ),
    BottomNavItem(
        directionClass = SettingsDirection::class,
        label = "Settings",
        icon = Icons.Default.Settings,
    ),
)
