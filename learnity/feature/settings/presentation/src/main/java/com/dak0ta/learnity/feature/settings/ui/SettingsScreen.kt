package com.dak0ta.learnity.feature.settings.ui

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import com.dak0ta.learnity.core.navigation.compose.LocalViewModelFactory
import com.dak0ta.learnity.feature.settings.presentation.SettingsViewModel

@Composable
internal fun SettingsScreen() {
    val viewModelFactory = LocalViewModelFactory.current
    val viewModel: SettingsViewModel = viewModel(factory = viewModelFactory)
    val state by viewModel.uiState.collectAsState()

    DisposableEffect(Unit) {
        Log.d("Learnity:SettingsScreen", "Composable created")
        onDispose {
            Log.d("Learnity:SettingsScreen", "Composable disposed")
        }
    }

    LaunchedEffect(Unit) {
        viewModel.initialize()
    }

    SettingsScreenContent(
        state = state,
        onRetryClick = viewModel::onRetryClick,
        onThemeSelect = viewModel::onThemeSelect,
    )
}
