package com.dak0ta.learnity.feature.home.data.usecase

import com.dak0ta.learnity.core.domain.Quote
import com.dak0ta.learnity.feature.home.data.repository.QuotesRepository
import com.dak0ta.learnity.feature.home.domain.usecase.GetQuotesUseCase
import javax.inject.Inject

internal class GetQuotesUseCaseImpl @Inject constructor(
    private val repository: QuotesRepository,
) : GetQuotesUseCase {

    override suspend fun invoke(): List<Quote> {
        return repository.getQuotes()
    }
}
