package com.dak0ta.learnity.feature.home.data.usecase

import com.dak0ta.learnity.core.domain.Quote
import com.dak0ta.learnity.feature.home.data.repository.QuotesRepository
import com.dak0ta.learnity.feature.home.domain.usecase.ObserveQuotesUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

internal class ObserveQuotesUseCaseImpl @Inject constructor(
    private val repository: QuotesRepository,
) : ObserveQuotesUseCase {

    override fun invoke(): Flow<List<Quote>> {
        return repository.observeQuotesCache()
    }
}
