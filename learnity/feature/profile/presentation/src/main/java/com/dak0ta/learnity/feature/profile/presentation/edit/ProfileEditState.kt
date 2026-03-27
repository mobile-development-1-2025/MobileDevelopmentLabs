package com.dak0ta.learnity.feature.profile.presentation.edit

import com.dak0ta.learnity.core.domain.User
import com.dak0ta.learnity.feature.profile.presentation.edit.ui.EditableFieldType

internal sealed interface ProfileEditState {

    object Loading : ProfileEditState

    data class Content(
        val user: User,
        val isSaving: Boolean,
        val validationErrors: Map<EditableFieldType, String>,
    ) : ProfileEditState

    object Error : ProfileEditState
}
