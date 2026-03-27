package com.dak0ta.learnity.feature.authorization.ui

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import com.dak0ta.learnity.core.navigation.compose.LocalNavController
import com.dak0ta.learnity.core.navigation.compose.LocalViewModelFactory
import com.dak0ta.learnity.core.navigation.compose.navigateTo
import com.dak0ta.learnity.core.navigation.compose.popUpToDirection
import com.dak0ta.learnity.feature.authorization.domain.navigation.AuthorizationDirection
import com.dak0ta.learnity.feature.authorization.presentation.AuthorizationAction
import com.dak0ta.learnity.feature.authorization.presentation.AuthorizationViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@Composable
internal fun AuthorizationScreen() {
    val viewModelFactory = LocalViewModelFactory.current
    val navController = LocalNavController.current
    val viewModel: AuthorizationViewModel = viewModel(factory = viewModelFactory)
    val state by viewModel.uiState.collectAsState()

    DisposableEffect(Unit) {
        Log.d("Learnity:AuthorizationScreen", "Composable created")
        onDispose {
            Log.d("Learnity:AuthorizationScreen", "Composable disposed")
        }
    }

    LaunchedEffect(Unit) {
        viewModel.initialize()

        viewModel.action
            .onEach { action ->
                when (action) {
                    is AuthorizationAction.NavigateTo -> {
                        navController.navigateTo(action.directionClass) {
                            popUpToDirection(AuthorizationDirection::class, inclusive = true)
                        }
                    }
                }
            }
            .launchIn(this)
    }

    AuthorizationScreenContent(
        state = state,
        onRetryClick = viewModel::onRetryClick,
    )
}
