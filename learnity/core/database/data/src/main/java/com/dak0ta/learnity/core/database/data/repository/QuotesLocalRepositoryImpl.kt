package com.dak0ta.learnity.core.database.data.repository

import com.dak0ta.learnity.core.database.data.dao.QuotesDao
import com.dak0ta.learnity.core.database.data.mapper.toDomain
import com.dak0ta.learnity.core.database.data.mapper.toEntity
import com.dak0ta.learnity.core.database.domain.repository.QuotesLocalRepository
import com.dak0ta.learnity.core.domain.Quote
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Singleton

@Singleton
internal class QuotesLocalRepositoryImpl(
    private val dao: QuotesDao,
) : QuotesLocalRepository {

    override fun observeQuotes(): Flow<List<Quote>> {
        return dao.getAllFlow().map { it.map { e -> e.toDomain() } }
    }

    override suspend fun getQuotes(): List<Quote> {
        return dao.getAllOnce().map { it.toDomain() }
    }

    override suspend fun upsertQuotes(quotes: List<Quote>) {
        dao.insertAll(quotes.map { it.toEntity() })
    }

    override suspend fun deleteQuotes() {
        dao.deleteAll()
    }
}
