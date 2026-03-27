package com.dak0ta.learnity.feature.authorization.presentation.ui

internal sealed interface AuthorizationUiState {

    object Loading : AuthorizationUiState

    object Content : AuthorizationUiState

    data class Error(
        val title: String,
        val description: String,
        val retryButtonText: String,
    ) : AuthorizationUiState
}
