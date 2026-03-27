package com.dak0ta.learnity.feature.profile.presentation.navigation

import androidx.navigation.NavGraphBuilder
import com.dak0ta.learnity.core.navigation.compose.composableDirection
import com.dak0ta.learnity.core.navigation.compose.navigationDirection
import com.dak0ta.learnity.feature.profile.domain.navigation.ProfileDirection
import com.dak0ta.learnity.feature.profile.ui.edit.ProfileEditScreen
import com.dak0ta.learnity.feature.profile.ui.profile.ProfileScreen

fun NavGraphBuilder.navigationProfile() {
    navigationDirection<ProfileDirection>(
        start = ProfileRootDirection::class,
    ) {
        composableDirection<ProfileRootDirection> {
            ProfileScreen()
        }
        composableDirection<ProfileEditDirection> {
            ProfileEditScreen()
        }
    }
}
