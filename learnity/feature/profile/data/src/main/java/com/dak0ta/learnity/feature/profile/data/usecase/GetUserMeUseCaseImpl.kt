package com.dak0ta.learnity.feature.profile.data.usecase

import com.dak0ta.learnity.core.domain.User
import com.dak0ta.learnity.feature.profile.data.repository.UserRepository
import com.dak0ta.learnity.feature.profile.domain.usecase.GetUserMeUseCase
import javax.inject.Inject

internal class GetUserMeUseCaseImpl @Inject constructor(
    private val repository: UserRepository,
) : GetUserMeUseCase {

    override suspend fun invoke(): User {
        return repository.getUserMe()
    }
}
