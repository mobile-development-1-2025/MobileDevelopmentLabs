package com.dak0ta.learnity.feature.authorization.presentation.ui

import com.dak0ta.learnity.feature.authorization.presentation.AuthorizationState
import javax.inject.Inject

internal class AuthorizationUiStateMapper @Inject constructor() : (AuthorizationState) -> AuthorizationUiState {

    override fun invoke(state: AuthorizationState): AuthorizationUiState {
        return when (state) {
            is AuthorizationState.Loading -> AuthorizationUiState.Loading
            is AuthorizationState.Content -> AuthorizationUiState.Content
            is AuthorizationState.Error -> mapErrorState()
        }
    }

    private fun mapErrorState(): AuthorizationUiState.Error = AuthorizationUiState.Error(
        title = "Something went wrong",
        description = "The data could not be uploaded. Try again later.",
        retryButtonText = "Try again",
    )
}
