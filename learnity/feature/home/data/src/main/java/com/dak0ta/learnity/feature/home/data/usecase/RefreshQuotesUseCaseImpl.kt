package com.dak0ta.learnity.feature.home.data.usecase

import com.dak0ta.learnity.feature.home.data.repository.QuotesRepository
import com.dak0ta.learnity.feature.home.domain.usecase.RefreshQuotesUseCase
import javax.inject.Inject

internal class RefreshQuotesUseCaseImpl @Inject constructor(
    private val repository: QuotesRepository,
) : RefreshQuotesUseCase {

    override suspend fun invoke() {
        repository.getQuotes(forceUpdate = true)
    }
}
