package com.dak0ta.learnity.feature.profile.domain.usecase

import com.dak0ta.learnity.core.domain.User
import kotlinx.coroutines.flow.Flow

interface ObserveUserMeUseCase {

    operator fun invoke(id: Int): Flow<User?>
}
