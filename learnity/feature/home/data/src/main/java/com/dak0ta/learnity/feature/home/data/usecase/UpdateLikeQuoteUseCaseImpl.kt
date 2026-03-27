package com.dak0ta.learnity.feature.home.data.usecase

import com.dak0ta.learnity.feature.home.data.repository.QuotesRepository
import com.dak0ta.learnity.feature.home.domain.usecase.UpdateLikeQuoteUseCase
import javax.inject.Inject

internal class UpdateLikeQuoteUseCaseImpl @Inject constructor(
    private val repository: QuotesRepository,
) : UpdateLikeQuoteUseCase {

    override suspend fun invoke(quoteId: Int, isLiked: Boolean) {
        repository.updateLikeQuote(quoteId, isLiked)
    }
}
