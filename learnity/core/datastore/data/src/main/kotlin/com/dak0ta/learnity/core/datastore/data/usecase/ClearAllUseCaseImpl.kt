package com.dak0ta.learnity.core.datastore.data.usecase

import com.dak0ta.learnity.core.datastore.data.repository.UserPreferencesRepository
import com.dak0ta.learnity.core.datastore.domain.usecase.ClearAllUseCase
import javax.inject.Inject

internal class ClearAllUseCaseImpl @Inject constructor(
    private val repository: UserPreferencesRepository,
) : ClearAllUseCase {

    override suspend fun invoke() {
        repository.clearAll()
    }
}
