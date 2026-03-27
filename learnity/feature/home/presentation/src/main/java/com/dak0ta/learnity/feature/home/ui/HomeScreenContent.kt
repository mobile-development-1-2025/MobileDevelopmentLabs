package com.dak0ta.learnity.feature.home.ui

import androidx.compose.runtime.Composable
import com.dak0ta.learnity.core.design.ErrorScreen
import com.dak0ta.learnity.core.design.LoadingScreen
import com.dak0ta.learnity.feature.home.presentation.ui.HomeUiState
import com.dak0ta.learnity.feature.home.ui.widget.QuoteList

@Composable
internal fun HomeScreenContent(
    state: HomeUiState,
    onLikeClick: (Int, Boolean) -> Unit,
    onRefresh: () -> Unit,
    onRetryClick: () -> Unit,
) {
    when (state) {
        is HomeUiState.Loading -> {
            LoadingScreen()
        }

        is HomeUiState.Content -> {
            QuoteList(
                state = state,
                onLikeClick = onLikeClick,
                onRefresh = onRefresh,
            )
        }

        is HomeUiState.Error -> {
            ErrorScreen(
                title = state.title,
                description = state.description,
                retryButtonText = state.retryButtonText,
                onRetryClick = onRetryClick,
            )
        }
    }
}
