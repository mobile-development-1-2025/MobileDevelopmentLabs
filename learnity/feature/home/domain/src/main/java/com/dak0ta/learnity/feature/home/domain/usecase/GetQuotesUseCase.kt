package com.dak0ta.learnity.feature.home.domain.usecase

import com.dak0ta.learnity.core.domain.Quote

interface GetQuotesUseCase {

    suspend operator fun invoke(): List<Quote>
}
