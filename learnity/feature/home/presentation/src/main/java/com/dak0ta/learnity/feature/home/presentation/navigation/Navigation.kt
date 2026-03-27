package com.dak0ta.learnity.feature.home.presentation.navigation

import androidx.navigation.NavGraphBuilder
import com.dak0ta.learnity.core.navigation.compose.composableDirection
import com.dak0ta.learnity.core.navigation.compose.navigationDirection
import com.dak0ta.learnity.feature.home.domain.navigation.HomeDirection
import com.dak0ta.learnity.feature.home.ui.HomeScreen

fun NavGraphBuilder.navigationHome() {
    navigationDirection<HomeDirection>(
        start = HomeRootDirection::class,
    ) {
        composableDirection<HomeRootDirection> {
            HomeScreen()
        }
    }
}
