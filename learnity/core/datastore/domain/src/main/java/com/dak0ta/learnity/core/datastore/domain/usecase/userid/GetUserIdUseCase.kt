package com.dak0ta.learnity.core.datastore.domain.usecase.userid

interface GetUserIdUseCase {

    suspend operator fun invoke(): Int?
}
