package com.dak0ta.learnity.feature.settings.presentation.ui

import com.dak0ta.learnity.core.domain.AppTheme

internal data class ThemeOption(
    val theme: AppTheme,
    val label: String,
    val isSelected: Boolean,
)
