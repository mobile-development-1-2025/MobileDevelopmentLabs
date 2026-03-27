package com.dak0ta.learnity.feature.profile.presentation.edit

internal sealed interface ProfileEditAction {

    object NavigateBack : ProfileEditAction

    class ShowMessage(
        val title: String,
        val description: String,
    ) : ProfileEditAction
}
