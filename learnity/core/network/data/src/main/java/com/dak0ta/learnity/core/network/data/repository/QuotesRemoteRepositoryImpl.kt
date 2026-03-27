package com.dak0ta.learnity.core.network.data.repository

import android.util.Log
import com.dak0ta.learnity.core.domain.Quote
import com.dak0ta.learnity.core.network.data.api.service.QuoteService
import com.dak0ta.learnity.core.network.data.mapper.toDomain
import com.dak0ta.learnity.core.network.data.network.SafeApiCall
import com.dak0ta.learnity.core.network.domain.model.ApiResult
import com.dak0ta.learnity.core.network.domain.repository.QuotesRemoteRepository

internal class QuotesRemoteRepositoryImpl(
    private val service: QuoteService,
    private val safeApiCall: SafeApiCall,
) : QuotesRemoteRepository {

    override suspend fun getQuotes(): ApiResult<List<Quote>> {
        Log.d(TAG, "Getting quotes")
        return safeApiCall("GET_QUOTES") {
            service.getQuotes().quotes.map { it.toDomain() }
        }
    }

    private companion object {

        const val TAG = "Learnity:QuotesRemoteRepositoryImpl"
    }
}
