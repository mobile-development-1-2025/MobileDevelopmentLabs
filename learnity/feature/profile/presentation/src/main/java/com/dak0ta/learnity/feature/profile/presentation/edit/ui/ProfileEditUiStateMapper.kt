package com.dak0ta.learnity.feature.profile.presentation.edit.ui

import com.dak0ta.learnity.feature.profile.presentation.edit.ProfileEditState
import javax.inject.Inject

internal class ProfileEditUiStateMapper @Inject constructor() : (ProfileEditState) -> ProfileEditUiState {

    override fun invoke(state: ProfileEditState): ProfileEditUiState {
        return when (state) {
            is ProfileEditState.Loading -> ProfileEditUiState.Loading
            is ProfileEditState.Content -> mapContentState(state)
            is ProfileEditState.Error -> mapErrorState()
        }
    }

    private fun mapContentState(state: ProfileEditState.Content) = ProfileEditUiState.Content(
        title = "Editing",
        firstNameField = createFieldState(
            title = "First name",
            value = state.user.firstName,
            isSaving = state.isSaving,
            error = state.validationErrors[EditableFieldType.FIRST_NAME],
        ),
        lastNameField = createFieldState(
            title = "Last name",
            value = state.user.lastName,
            isSaving = state.isSaving,
            error = state.validationErrors[EditableFieldType.LAST_NAME],
        ),
        button = createButtonState(state.isSaving, state.validationErrors),
    )

    private fun createFieldState(
        title: String,
        value: String,
        isSaving: Boolean,
        error: String?,
    ): FieldState {
        return when {
            isSaving -> FieldState.Disabled(title, value)
            error != null -> FieldState.Invalid(title, value, error)
            else -> FieldState.Valid(title, value)
        }
    }

    private fun createButtonState(isSaving: Boolean, errors: Map<EditableFieldType, String?>): ButtonState {
        return when {
            isSaving -> ButtonState.Loading("Saving...")
            errors.isNotEmpty() -> ButtonState.Disabled("Save")
            else -> ButtonState.Enabled("Save")
        }
    }

    private fun mapErrorState(): ProfileEditUiState.Error = ProfileEditUiState.Error(
        title = "Something went wrong",
        description = "The data could not be uploaded. Try again later.",
        retryButtonText = "Try again",
    )
}
