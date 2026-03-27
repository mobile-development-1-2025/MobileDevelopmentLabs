package com.dak0ta.learnity.feature.profile.data.usecase

import com.dak0ta.learnity.core.domain.User
import com.dak0ta.learnity.feature.profile.data.repository.UserRepository
import com.dak0ta.learnity.feature.profile.domain.usecase.ObserveUserMeUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

internal class ObserveUserMeUseCaseImpl @Inject constructor(
    private val repository: UserRepository,
) : ObserveUserMeUseCase {

    override fun invoke(id: Int): Flow<User?> {
        return repository.observeUserMeCache(id)
    }
}
