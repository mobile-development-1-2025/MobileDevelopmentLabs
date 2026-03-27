package com.dak0ta.learnity.feature.home.data.di

import com.dak0ta.learnity.feature.home.data.repository.QuotesRepository
import com.dak0ta.learnity.feature.home.data.repository.QuotesRepositoryImpl
import com.dak0ta.learnity.feature.home.data.usecase.GetQuotesUseCaseImpl
import com.dak0ta.learnity.feature.home.data.usecase.ObserveQuotesUseCaseImpl
import com.dak0ta.learnity.feature.home.data.usecase.RefreshQuotesUseCaseImpl
import com.dak0ta.learnity.feature.home.data.usecase.UpdateLikeQuoteUseCaseImpl
import com.dak0ta.learnity.feature.home.domain.usecase.GetQuotesUseCase
import com.dak0ta.learnity.feature.home.domain.usecase.ObserveQuotesUseCase
import com.dak0ta.learnity.feature.home.domain.usecase.RefreshQuotesUseCase
import com.dak0ta.learnity.feature.home.domain.usecase.UpdateLikeQuoteUseCase
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
abstract class HomeDataModule {

    @Binds
    @Singleton
    internal abstract fun bindQuotesRepository(
        impl: QuotesRepositoryImpl,
    ): QuotesRepository

    @Binds
    internal abstract fun bindGetQuotesUseCase(
        impl: GetQuotesUseCaseImpl,
    ): GetQuotesUseCase

    @Binds
    internal abstract fun bindObserveQuotesUseCase(
        impl: ObserveQuotesUseCaseImpl,
    ): ObserveQuotesUseCase

    @Binds
    internal abstract fun bindRefreshQuotesUseCase(
        impl: RefreshQuotesUseCaseImpl,
    ): RefreshQuotesUseCase

    @Binds
    internal abstract fun bindUpdateLikeQuoteUseCase(
        impl: UpdateLikeQuoteUseCaseImpl,
    ): UpdateLikeQuoteUseCase
}
