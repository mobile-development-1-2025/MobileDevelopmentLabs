package com.dak0ta.learnity.feature.home.domain.usecase

interface UpdateLikeQuoteUseCase {

    suspend operator fun invoke(quoteId: Int, isLiked: Boolean)
}
