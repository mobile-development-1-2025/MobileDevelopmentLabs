package com.dak0ta.learnity.feature.profile.data.usecase

import com.dak0ta.learnity.core.domain.User
import com.dak0ta.learnity.feature.profile.data.repository.UserRepository
import com.dak0ta.learnity.feature.profile.domain.usecase.UpdateUserMeUseCase
import javax.inject.Inject

internal class UpdateUserMeUseCaseImpl @Inject constructor(
    private val repository: UserRepository,
) : UpdateUserMeUseCase {

    override suspend fun invoke(user: User) {
        repository.updateUser(user)
    }
}
