package com.dak0ta.learnity.feature.settings.presentation.navigation

import androidx.navigation.NavGraphBuilder
import com.dak0ta.learnity.core.navigation.compose.composableDirection
import com.dak0ta.learnity.core.navigation.compose.navigationDirection
import com.dak0ta.learnity.feature.settings.domain.SettingsDirection
import com.dak0ta.learnity.feature.settings.ui.SettingsScreen

fun NavGraphBuilder.navigationSettings() {
    navigationDirection<SettingsDirection>(
        start = SettingsRootDirection::class,
    ) {
        composableDirection<SettingsRootDirection> {
            SettingsScreen()
        }
    }
}
