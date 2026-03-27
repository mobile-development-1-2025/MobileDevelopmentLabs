package com.dak0ta.learnity.core.datastore.domain.usecase.userid

interface UpdateUserIdUseCase {

    suspend operator fun invoke(id: Int?)
}
