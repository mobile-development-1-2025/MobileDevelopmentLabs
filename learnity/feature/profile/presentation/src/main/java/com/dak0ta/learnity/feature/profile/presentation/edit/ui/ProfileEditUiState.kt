package com.dak0ta.learnity.feature.profile.presentation.edit.ui

internal sealed interface ProfileEditUiState {

    object Loading : ProfileEditUiState

    data class Content(
        val title: String,
        val firstNameField: FieldState,
        val lastNameField: FieldState,
        val button: ButtonState,
    ) : ProfileEditUiState

    data class Error(
        val title: String,
        val description: String,
        val retryButtonText: String,
    ) : ProfileEditUiState
}
