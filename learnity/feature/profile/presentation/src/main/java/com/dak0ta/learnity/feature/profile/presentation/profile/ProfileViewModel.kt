package com.dak0ta.learnity.feature.profile.presentation.profile

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.dak0ta.learnity.core.coroutine.runSuspendCatching
import com.dak0ta.learnity.core.mvvm.BaseViewModel
import com.dak0ta.learnity.feature.profile.domain.usecase.GetUserMeUseCase
import com.dak0ta.learnity.feature.profile.domain.usecase.ObserveUserMeUseCase
import com.dak0ta.learnity.feature.profile.presentation.navigation.ProfileEditDirection
import com.dak0ta.learnity.feature.profile.presentation.profile.ui.ProfileUiState
import com.dak0ta.learnity.feature.profile.presentation.profile.ui.ProfileUiStateMapper
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

internal class ProfileViewModel @Inject constructor(
    private val getUserMeUseCase: GetUserMeUseCase,
    private val observeUserMeUseCase: ObserveUserMeUseCase,
    uiStateMapper: ProfileUiStateMapper,
) : BaseViewModel() {

    private val dataState = MutableStateFlow<ProfileState>(ProfileState.Loading)
    val uiState: StateFlow<ProfileUiState> = dataState.map(uiStateMapper)
        .stateInViewModel(ProfileUiState.Loading)
    private val _action = Channel<ProfileAction>(Channel.BUFFERED)
    val action: Flow<ProfileAction> = _action.receiveAsFlow()

    override fun onFirstInit() {
        loadData()
    }

    private fun loadData() {
        viewModelScope.launch {
            runSuspendCatching {
                getUserMeUseCase()
            }
                .onSuccess { user ->
                    dataState.value = ProfileState.Content(user)
                    observeUser(user.id)
                }
                .onFailure {
                    Log.e(TAG, "Data loading failed", it)
                    dataState.value = ProfileState.Error
                }
        }
    }

    private fun observeUser(id: Int) {
        viewModelScope.launch {
            observeUserMeUseCase(id).onEach { user ->
                if (user != null) {
                    dataState.update { currentState ->
                        if (currentState !is ProfileState.Content) return@update currentState
                        currentState.copy(user = user)
                    }
                }
            }
                .launchIn(this)
        }
    }

    internal fun onEditButtonClick() {
        viewModelScope.launch {
            _action.send(
                ProfileAction.NavigateTo(ProfileEditDirection::class)
            )
        }
    }

    internal fun onRetryClick() {
        dataState.update { ProfileState.Loading }
        loadData()
    }

    private companion object {

        const val TAG = "Learnity:ProfileViewModel"
    }
}
