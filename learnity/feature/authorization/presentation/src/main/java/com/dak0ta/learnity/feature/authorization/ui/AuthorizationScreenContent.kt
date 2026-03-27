package com.dak0ta.learnity.feature.authorization.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.dak0ta.learnity.core.design.ErrorScreen
import com.dak0ta.learnity.core.design.LoadingScreen
import com.dak0ta.learnity.feature.authorization.presentation.ui.AuthorizationUiState

@Composable
internal fun AuthorizationScreenContent(
    state: AuthorizationUiState,
    onRetryClick: () -> Unit,
) {
    when (state) {
        is AuthorizationUiState.Loading -> {
            LoadingScreen()
        }

        is AuthorizationUiState.Content -> {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    text = "AuthorizationUiState.Content",
                )
            }
        }

        is AuthorizationUiState.Error -> {
            ErrorScreen(
                title = state.title,
                description = state.description,
                retryButtonText = state.retryButtonText,
                onRetryClick = onRetryClick,
            )
        }
    }
}
