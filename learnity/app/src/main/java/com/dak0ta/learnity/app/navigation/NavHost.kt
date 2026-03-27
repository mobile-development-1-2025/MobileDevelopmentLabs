package com.dak0ta.learnity.app.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.dak0ta.learnity.core.navigation.compose.LocalNavController
import com.dak0ta.learnity.core.navigation.compose.LocalViewModelFactory
import com.dak0ta.learnity.core.navigation.directionRouteOf
import com.dak0ta.learnity.feature.authorization.domain.navigation.AuthorizationDirection
import com.dak0ta.learnity.feature.authorization.presentation.navigation.navigationAuthorization
import com.dak0ta.learnity.feature.home.presentation.navigation.navigationHome
import com.dak0ta.learnity.feature.profile.presentation.navigation.navigationProfile
import com.dak0ta.learnity.feature.settings.presentation.navigation.navigationSettings

@Composable
fun NavHost(
    navController: NavHostController,
    viewModelFactory: ViewModelProvider.Factory,
    modifier: Modifier = Modifier,
) {
    CompositionLocalProvider(
        LocalViewModelFactory provides viewModelFactory,
        LocalNavController provides navController,
    ) {
        NavHost(
            navController = navController,
            startDestination = directionRouteOf(AuthorizationDirection::class),
            modifier = modifier,
        ) {
            navigationAuthorization()
            navigationHome()
            navigationSettings()
            navigationProfile()
        }
    }
}
