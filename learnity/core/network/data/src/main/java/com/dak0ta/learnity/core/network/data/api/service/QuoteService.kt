package com.dak0ta.learnity.core.network.data.api.service

import com.dak0ta.learnity.core.network.data.api.dto.QuotesResponseDto
import retrofit2.http.GET

internal interface QuoteService {

    @GET("quotes")
    suspend fun getQuotes(): QuotesResponseDto
}
