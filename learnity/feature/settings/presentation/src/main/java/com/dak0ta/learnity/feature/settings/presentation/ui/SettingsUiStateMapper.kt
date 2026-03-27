package com.dak0ta.learnity.feature.settings.presentation.ui

import com.dak0ta.learnity.core.domain.AppTheme
import com.dak0ta.learnity.feature.settings.presentation.SettingsState
import javax.inject.Inject

internal class SettingsUiStateMapper @Inject constructor() : (SettingsState) -> SettingsUiState {

    override fun invoke(state: SettingsState): SettingsUiState {
        return when (state) {
            is SettingsState.Loading -> SettingsUiState.Loading
            is SettingsState.Content -> mapContentState(state.appTheme)
            is SettingsState.Error -> mapErrorState()
        }
    }

    private fun mapContentState(appTheme: AppTheme): SettingsUiState.Content {
        val themeOptions = listOf(
            ThemeOption(AppTheme.LIGHT, "Light", appTheme == AppTheme.LIGHT),
            ThemeOption(AppTheme.DARK, "Dark", appTheme == AppTheme.DARK),
            ThemeOption(AppTheme.SYSTEM_DEFAULT, "System default", appTheme == AppTheme.SYSTEM_DEFAULT),
        )
        return SettingsUiState.Content(themeOptions)
    }

    private fun mapErrorState(): SettingsUiState.Error = SettingsUiState.Error(
        title = "Something went wrong",
        description = "The data could not be uploaded. Try again later.",
        retryButtonText = "Try again",
    )
}
