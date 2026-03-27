package com.dak0ta.learnity.feature.home.data.repository

import com.dak0ta.learnity.core.coroutine.CoroutineDispatchers
import com.dak0ta.learnity.core.database.domain.cache.CacheManager
import com.dak0ta.learnity.core.database.domain.repository.QuotesLocalRepository
import com.dak0ta.learnity.core.domain.Quote
import com.dak0ta.learnity.core.network.domain.model.ApiResult
import com.dak0ta.learnity.core.network.domain.repository.QuotesRemoteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class QuotesRepositoryImpl @Inject constructor(
    private val local: QuotesLocalRepository,
    private val remote: QuotesRemoteRepository,
    private val cacheManager: CacheManager,
    private val dispatchers: CoroutineDispatchers,
) : QuotesRepository {

    override suspend fun getQuotes(forceUpdate: Boolean): List<Quote> = withContext(dispatchers.io) {
        val isCacheActual = cacheManager.isCacheActual(CACHE_KEY_QUOTE_LIST)
        if (!forceUpdate && isCacheActual) {
            local.getQuotes()
        } else {
            fetchAndCacheQuotes()
        }
    }

    override suspend fun updateLikeQuote(quoteId: Int, isLiked: Boolean) {
        val quotes = local.getQuotes()
        val updated = quotes.map {
            if (it.id == quoteId) it.copy(isLiked = isLiked) else it
        }
        local.upsertQuotes(updated)
    }

    override fun observeQuotesCache(): Flow<List<Quote>> {
        return local.observeQuotes().distinctUntilChanged()
    }

    private suspend fun fetchAndCacheQuotes(): List<Quote> {
        return when (val result = remote.getQuotes()) {
            is ApiResult.Success -> {
                val remoteQuotes = result.data
                val localQuotes = local.getQuotes().associateBy { it.id }

                val merged = remoteQuotes.map { remote ->
                    val local = localQuotes[remote.id]
                    if (local != null) {
                        remote.copy(isLiked = local.isLiked)
                    } else {
                        remote.copy(isLiked = false)
                    }
                }

                local.upsertQuotes(merged)
                cacheManager.updateCacheTimestamp(CACHE_KEY_QUOTE_LIST)
                merged
            }

            is ApiResult.Failure -> {
                val cached = local.getQuotes()
                cached.ifEmpty { error("Failed to load quotes and no cache available") }
            }
        }
    }

    private companion object {

        const val CACHE_KEY_QUOTE_LIST = "cache_quote_list"
    }
}
