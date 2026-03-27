package com.dak0ta.learnity.feature.profile.data.di

import com.dak0ta.learnity.feature.profile.data.repository.UserRepository
import com.dak0ta.learnity.feature.profile.data.repository.UserRepositoryImpl
import com.dak0ta.learnity.feature.profile.data.usecase.GetUserMeUseCaseImpl
import com.dak0ta.learnity.feature.profile.data.usecase.ObserveUserMeUseCaseImpl
import com.dak0ta.learnity.feature.profile.data.usecase.UpdateUserMeUseCaseImpl
import com.dak0ta.learnity.feature.profile.domain.usecase.GetUserMeUseCase
import com.dak0ta.learnity.feature.profile.domain.usecase.ObserveUserMeUseCase
import com.dak0ta.learnity.feature.profile.domain.usecase.UpdateUserMeUseCase
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
abstract class ProfileDataModule {

    @Binds
    @Singleton
    internal abstract fun bindUserRepository(
        impl: UserRepositoryImpl,
    ): UserRepository

    @Binds
    internal abstract fun bindGetUserMeUseCase(
        impl: GetUserMeUseCaseImpl,
    ): GetUserMeUseCase

    @Binds
    internal abstract fun bindObserveUserMeUseCase(
        impl: ObserveUserMeUseCaseImpl,
    ): ObserveUserMeUseCase

    @Binds
    internal abstract fun bindUpdateUserMeUseCase(
        impl: UpdateUserMeUseCaseImpl,
    ): UpdateUserMeUseCase
}
