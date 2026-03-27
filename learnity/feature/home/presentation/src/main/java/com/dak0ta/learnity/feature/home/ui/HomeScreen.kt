package com.dak0ta.learnity.feature.home.ui

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import com.dak0ta.learnity.core.navigation.compose.LocalViewModelFactory
import com.dak0ta.learnity.feature.home.presentation.HomeViewModel

@Composable
internal fun HomeScreen() {
    val viewModelFactory = LocalViewModelFactory.current
    val viewModel: HomeViewModel = viewModel(factory = viewModelFactory)
    val state by viewModel.uiState.collectAsState()

    DisposableEffect(Unit) {
        Log.d("Learnity:HomeScreen", "Composable created")
        onDispose {
            Log.d("Learnity:HomeScreen", "Composable disposed")
        }
    }

    LaunchedEffect(Unit) {
        viewModel.initialize()
    }

    HomeScreenContent(
        state = state,
        onLikeClick = viewModel::onLikeClick,
        onRefresh = viewModel::onRefresh,
        onRetryClick = viewModel::onRetryClick,
    )
}
