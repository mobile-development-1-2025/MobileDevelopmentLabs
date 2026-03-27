package com.dak0ta.learnity.feature.authorization.presentation

internal sealed interface AuthorizationState {

    object Loading : AuthorizationState

    object Content : AuthorizationState

    object Error : AuthorizationState
}
