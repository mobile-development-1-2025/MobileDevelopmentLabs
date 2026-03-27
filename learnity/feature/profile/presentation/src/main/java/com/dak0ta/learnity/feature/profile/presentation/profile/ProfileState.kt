package com.dak0ta.learnity.feature.profile.presentation.profile

import com.dak0ta.learnity.core.domain.User

internal sealed interface ProfileState {

    object Loading : ProfileState

    data class Content(val user: User) : ProfileState

    object Error : ProfileState
}
