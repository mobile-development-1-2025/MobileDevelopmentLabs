package com.dak0ta.learnity.feature.profile.presentation.profile.ui

import com.dak0ta.learnity.core.domain.Gender
import com.dak0ta.learnity.core.domain.User
import com.dak0ta.learnity.feature.profile.presentation.profile.ProfileState
import javax.inject.Inject

internal class ProfileUiStateMapper @Inject constructor() : (ProfileState) -> ProfileUiState {

    override fun invoke(state: ProfileState): ProfileUiState {
        return when (state) {
            is ProfileState.Loading -> ProfileUiState.Loading
            is ProfileState.Content -> mapContentState(state)
            is ProfileState.Error -> mapErrorState()
        }
    }

    private fun mapContentState(state: ProfileState.Content): ProfileUiState.Content =
        ProfileUiState.Content(
            mapUserToUi(state.user),
        )

    private fun mapUserToUi(user: User) = UserInfo(
        email = "Email: ${user.email}",
        username = "Username: ${user.username}",
        firstName = "First name: ${user.firstName}",
        lastName = "Last name: ${user.lastName}",
        gender = "Gender: ${mapGenderToString(user.gender)}",
        image = user.image,
    )

    private fun mapGenderToString(gender: Gender): String {
        return when (gender) {
            Gender.MALE -> "Male"
            Gender.FEMALE -> "Female"
        }
    }

    private fun mapErrorState(): ProfileUiState.Error = ProfileUiState.Error(
        title = "Something went wrong",
        description = "The data could not be uploaded. Try again later.",
        retryButtonText = "Try again",
    )
}
