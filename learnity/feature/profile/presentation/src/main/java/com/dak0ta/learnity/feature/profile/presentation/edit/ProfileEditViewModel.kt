package com.dak0ta.learnity.feature.profile.presentation.edit

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.dak0ta.learnity.core.coroutine.runSuspendCatching
import com.dak0ta.learnity.core.mvvm.BaseViewModel
import com.dak0ta.learnity.feature.profile.domain.usecase.GetUserMeUseCase
import com.dak0ta.learnity.feature.profile.domain.usecase.UpdateUserMeUseCase
import com.dak0ta.learnity.feature.profile.presentation.edit.ui.EditableFieldType
import com.dak0ta.learnity.feature.profile.presentation.edit.ui.ProfileEditUiState
import com.dak0ta.learnity.feature.profile.presentation.edit.ui.ProfileEditUiStateMapper
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

internal class ProfileEditViewModel @Inject constructor(
    private val getUserMeUseCase: GetUserMeUseCase,
    private val updateUserMeUseCase: UpdateUserMeUseCase,
    uiStateMapper: ProfileEditUiStateMapper,
) : BaseViewModel() {

    private val dataState = MutableStateFlow<ProfileEditState>(ProfileEditState.Loading)
    val uiState: StateFlow<ProfileEditUiState> = dataState.map(uiStateMapper)
        .stateInViewModel(ProfileEditUiState.Loading)
    private val _action = Channel<ProfileEditAction>(Channel.BUFFERED)
    val action: Flow<ProfileEditAction> = _action.receiveAsFlow()

    override fun onFirstInit() {
        loadData()
    }

    private fun loadData() {
        viewModelScope.launch {
            runSuspendCatching {
                getUserMeUseCase()
            }
                .onSuccess { user ->
                    dataState.value = ProfileEditState.Content(
                        user = user,
                        isSaving = false,
                        validationErrors = emptyMap(),
                    )
                }
                .onFailure {
                    Log.e(TAG, "Data loading failed", it)
                    dataState.value = ProfileEditState.Error
                }
        }
    }

    internal fun onValueChange(field: EditableFieldType, newValue: String) {
        dataState.update { currentState ->
            if (currentState !is ProfileEditState.Content) return

            val updatedUser = when (field) {
                EditableFieldType.FIRST_NAME -> currentState.user.copy(firstName = newValue)
                EditableFieldType.LAST_NAME -> currentState.user.copy(lastName = newValue)
            }
            val updatedValidationErrors = currentState.validationErrors - field

            currentState.copy(
                user = updatedUser,
                validationErrors = updatedValidationErrors,
            )
        }
    }

    internal fun onClearClick(field: EditableFieldType) {
        onValueChange(field, "")
    }

    internal fun onSaveClick() {
        val currentState = dataState.value
        if (currentState !is ProfileEditState.Content) return

        val updatedUser = currentState.user.copy(
            firstName = currentState.user.firstName.trim(),
            lastName = currentState.user.lastName.trim(),
            isLocallyEdited = true,
        )

        val errors = ValidationHelper.validateAllFields(updatedUser)

        if (errors.isNotEmpty()) {
            dataState.update { currentState.copy(validationErrors = errors) }
            return
        }

        dataState.update { currentState.copy(isSaving = true) }

        viewModelScope.launch {
            runSuspendCatching {
                updateUserMeUseCase(updatedUser)
            }
                .onSuccess {
                    _action.send(ProfileEditAction.NavigateBack)
                }
                .onFailure {
                    Log.e(TAG, "updateUserMeUseCase has failed", it)
                    _action.send(
                        ProfileEditAction.ShowMessage(
                            title = "Failed to perform action",
                            description = "Something went wrong. Try again later.",
                        ),
                    )
                    dataState.value = ProfileEditState.Content(
                        user = currentState.user,
                        isSaving = false,
                        validationErrors = emptyMap(),
                    )
                }
        }
    }

    internal fun onRetryClick() {
        dataState.update { ProfileEditState.Loading }
        loadData()
    }

    private companion object {

        const val TAG = "Learnity:ProfileEditViewModel"
    }
}
