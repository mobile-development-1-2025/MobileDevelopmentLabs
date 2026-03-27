package com.dak0ta.learnity.feature.settings.presentation.ui

internal sealed interface SettingsUiState {

    object Loading : SettingsUiState

    data class Content(val themes: List<ThemeOption>) : SettingsUiState

    data class Error(
        val title: String,
        val description: String,
        val retryButtonText: String,
    ) : SettingsUiState
}
