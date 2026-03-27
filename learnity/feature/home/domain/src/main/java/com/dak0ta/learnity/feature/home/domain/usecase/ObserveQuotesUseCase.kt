package com.dak0ta.learnity.feature.home.domain.usecase

import com.dak0ta.learnity.core.domain.Quote
import kotlinx.coroutines.flow.Flow

interface ObserveQuotesUseCase {

    operator fun invoke(): Flow<List<Quote>>
}
