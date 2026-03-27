package com.dak0ta.learnity.core.network.domain.repository

import com.dak0ta.learnity.core.domain.Quote
import com.dak0ta.learnity.core.network.domain.model.ApiResult

interface QuotesRemoteRepository {

    suspend fun getQuotes(): ApiResult<List<Quote>>
}
