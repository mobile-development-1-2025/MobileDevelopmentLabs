package com.dak0ta.learnity.feature.profile.ui.profile

import androidx.compose.runtime.Composable
import com.dak0ta.learnity.core.design.ErrorScreen
import com.dak0ta.learnity.core.design.LoadingScreen
import com.dak0ta.learnity.feature.profile.presentation.profile.ui.ProfileUiState
import com.dak0ta.learnity.feature.profile.ui.widget.profile.ProfileContent

@Composable
internal fun ProfileScreenContent(
    state: ProfileUiState,
    onEditButtonClick: () -> Unit,
    onRetryClick: () -> Unit,
) {
    when (state) {
        is ProfileUiState.Loading -> {
            LoadingScreen()
        }

        is ProfileUiState.Content -> {
            ProfileContent(state, onEditButtonClick)
        }

        is ProfileUiState.Error -> {
            ErrorScreen(
                title = state.title,
                description = state.description,
                retryButtonText = state.retryButtonText,
                onRetryClick = onRetryClick,
            )
        }
    }
}
