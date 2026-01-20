package com.privatemessenger.app.screens.auth

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.privatemessenger.app.common.model.UiState
import com.privatemessenger.app.screens.auth.data.AuthRepository
import com.privatemessenger.app.screens.auth.model.AuthUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    @ApplicationContext private val context: Context
) : ViewModel() {
    private val _uiState = MutableStateFlow(
        AuthUiState(
            isLoggedIn = authRepository.isAuthenticated()
        )
    )
    val uiState = _uiState.asStateFlow()

    fun signIn(username: String, password: String) {
        if (validateForm(username, password)) {
            _uiState.update {
                it.copy(
                    isNetworkRequestActive = true
                )
            }
            authRepository.signIn(username, password).onEach { state ->
                if (state is UiState.Success) {
                    _uiState.update {
                        it.copy(isLoggedIn = true, isNetworkRequestActive = false)
                    }
                } else {
                    _uiState.update {
                        it.copy(
                            isLoggedIn = false,
                            errorMessage = (state as? UiState.Error)?.message ?: "",
                            isNetworkRequestActive = false
                        )
                    }
                }
            }.launchIn(viewModelScope)
        }
    }

    fun clearMessages() {
        _uiState.value = _uiState.value.copy(errorMessage = "", infoMessage = "")
    }

    private fun validateForm(
        username: String? = null,
        password: String? = null,
    ): Boolean {
        _uiState.value = _uiState.value.copy(
            invalidUsername = username?.isEmpty() ?: _uiState.value.invalidUsername,
            invalidPassword = password?.isEmpty() ?: _uiState.value.invalidPassword
        )

        return (username == null || username.isNotEmpty())
                && (password == null || password.isNotEmpty())
    }

    fun refreshTokens() {
        authRepository.refreshToken().launchIn(CoroutineScope(Dispatchers.IO))
    }
}