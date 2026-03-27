package com.dak0ta.learnity.core.datastore.data.usecase.userid

import com.dak0ta.learnity.core.datastore.data.repository.UserPreferencesRepository
import com.dak0ta.learnity.core.datastore.domain.usecase.userid.UpdateUserIdUseCase
import javax.inject.Inject

internal class UpdateUserIdUseCaseImpl @Inject constructor(
    private val repository: UserPreferencesRepository,
) : UpdateUserIdUseCase {

    override suspend fun invoke(id: Int?) {
        repository.updateUserId(id)
    }
}
