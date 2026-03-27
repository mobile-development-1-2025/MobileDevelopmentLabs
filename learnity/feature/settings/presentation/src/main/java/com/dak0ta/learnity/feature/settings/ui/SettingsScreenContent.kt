package com.dak0ta.learnity.feature.settings.ui

import androidx.compose.runtime.Composable
import com.dak0ta.learnity.core.design.ErrorScreen
import com.dak0ta.learnity.core.design.LoadingScreen
import com.dak0ta.learnity.core.domain.AppTheme
import com.dak0ta.learnity.feature.settings.presentation.ui.SettingsUiState
import com.dak0ta.learnity.feature.settings.ui.widget.SettingsContent

@Composable
internal fun SettingsScreenContent(
    state: SettingsUiState,
    onThemeSelect: (AppTheme) -> Unit,
    onRetryClick: () -> Unit,
) {
    when (state) {
        is SettingsUiState.Loading -> {
            LoadingScreen()
        }

        is SettingsUiState.Content -> {
            SettingsContent(
                themeOptions = state.themes,
                onThemeSelect = onThemeSelect,
            )
        }

        is SettingsUiState.Error -> {
            ErrorScreen(
                title = state.title,
                description = state.description,
                retryButtonText = state.retryButtonText,
                onRetryClick = onRetryClick,
            )
        }
    }
}
