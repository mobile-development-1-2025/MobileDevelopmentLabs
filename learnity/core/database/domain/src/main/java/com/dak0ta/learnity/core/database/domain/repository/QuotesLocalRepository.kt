package com.dak0ta.learnity.core.database.domain.repository

import com.dak0ta.learnity.core.domain.Quote
import kotlinx.coroutines.flow.Flow

interface QuotesLocalRepository {

    fun observeQuotes(): Flow<List<Quote>>
    suspend fun getQuotes(): List<Quote>
    suspend fun upsertQuotes(quotes: List<Quote>)
    suspend fun deleteQuotes()
}
