package com.dak0ta.learnity.feature.profile.domain.usecase

import com.dak0ta.learnity.core.domain.User

interface UpdateUserMeUseCase {

    suspend operator fun invoke(user: User)
}
