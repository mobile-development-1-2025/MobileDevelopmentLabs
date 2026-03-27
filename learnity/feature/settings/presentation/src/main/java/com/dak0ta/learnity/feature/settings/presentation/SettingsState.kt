package com.dak0ta.learnity.feature.settings.presentation

import com.dak0ta.learnity.core.domain.AppTheme

internal sealed interface SettingsState {

    object Loading : SettingsState

    data class Content(val appTheme: AppTheme) : SettingsState

    object Error : SettingsState
}
