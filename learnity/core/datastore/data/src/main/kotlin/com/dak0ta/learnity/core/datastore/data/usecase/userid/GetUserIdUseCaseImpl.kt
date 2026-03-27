package com.dak0ta.learnity.core.datastore.data.usecase.userid

import com.dak0ta.learnity.core.datastore.data.repository.UserPreferencesRepository
import com.dak0ta.learnity.core.datastore.domain.usecase.userid.GetUserIdUseCase
import kotlinx.coroutines.flow.firstOrNull
import javax.inject.Inject

internal class GetUserIdUseCaseImpl @Inject constructor(
    private val repository: UserPreferencesRepository,
) : GetUserIdUseCase {

    override suspend fun invoke(): Int? = repository.userIdFlow.firstOrNull()
}
