package com.dak0ta.learnity.feature.profile.ui.profile

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
import com.dak0ta.learnity.feature.profile.presentation.profile.ProfileAction
import com.dak0ta.learnity.feature.profile.presentation.profile.ProfileViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@Composable
internal fun ProfileScreen() {
    val viewModelFactory = LocalViewModelFactory.current
    val navController = LocalNavController.current
    val viewModel: ProfileViewModel = viewModel(factory = viewModelFactory)
    val state by viewModel.uiState.collectAsState()

    DisposableEffect(Unit) {
        Log.d("Learnity:ProfileScreen", "Composable created")
        onDispose {
            Log.d("Learnity:ProfileScreen", "Composable disposed")
        }
    }

    LaunchedEffect(Unit) {
        viewModel.initialize()

        viewModel.action
            .onEach { action ->
                when (action) {
                    is ProfileAction.NavigateTo -> {
                        navController.navigateTo(action.directionClass)
                    }
                }
            }
            .launchIn(this)
    }

    ProfileScreenContent(
        state = state,
        onEditButtonClick = viewModel::onEditButtonClick,
        onRetryClick = viewModel::onRetryClick,
    )
}
