package com.dak0ta.learnity.feature.home.data.repository

import com.dak0ta.learnity.core.domain.Quote
import kotlinx.coroutines.flow.Flow

internal interface QuotesRepository {

    suspend fun getQuotes(forceUpdate: Boolean = false): List<Quote>
    suspend fun updateLikeQuote(quoteId: Int, isLiked: Boolean)
    fun observeQuotesCache(): Flow<List<Quote>>
}
