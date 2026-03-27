package com.dak0ta.learnity.feature.authorization.data.di

import com.dak0ta.learnity.feature.authorization.data.repository.AuthorizationRepository
import com.dak0ta.learnity.feature.authorization.data.repository.AuthorizationRepositoryImpl
import com.dak0ta.learnity.feature.authorization.data.usecase.LoginUseCaseImpl
import com.dak0ta.learnity.feature.authorization.domain.usecase.LoginUseCase
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
abstract class AuthorizationDataModule {

    @Binds
    @Singleton
    internal abstract fun bindAuthorizationRepository(
        impl: AuthorizationRepositoryImpl,
    ): AuthorizationRepository

    @Binds
    internal abstract fun bindLoginUseCase(
        impl: LoginUseCaseImpl,
    ): LoginUseCase
}
