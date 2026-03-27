package com.dak0ta.learnity.feature.authorization.domain.usecase

interface LoginUseCase {

    suspend operator fun invoke(username: String, password: String)
}
