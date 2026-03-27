package com.dak0ta.learnity.feature.authorization.presentation.navigation

import androidx.navigation.NavGraphBuilder
import com.dak0ta.learnity.core.navigation.compose.composableDirection
import com.dak0ta.learnity.core.navigation.compose.navigationDirection
import com.dak0ta.learnity.feature.authorization.domain.navigation.AuthorizationDirection
import com.dak0ta.learnity.feature.authorization.ui.AuthorizationScreen

fun NavGraphBuilder.navigationAuthorization() {
    navigationDirection<AuthorizationDirection>(
        start = AuthorizationRootDirection::class,
    ) {
        composableDirection<AuthorizationRootDirection> {
            AuthorizationScreen()
        }
    }
}
