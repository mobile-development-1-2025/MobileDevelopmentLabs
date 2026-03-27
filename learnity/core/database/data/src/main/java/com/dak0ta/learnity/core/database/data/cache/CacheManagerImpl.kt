package com.dak0ta.learnity.core.database.data.cache

import com.dak0ta.learnity.core.coroutine.CoroutineDispatchers
import com.dak0ta.learnity.core.database.data.dao.CacheTimestampDao
import com.dak0ta.learnity.core.database.data.entity.CacheTimestampEntity
import com.dak0ta.learnity.core.database.domain.cache.CacheManager
import kotlinx.coroutines.withContext
import javax.inject.Singleton
import kotlin.time.Duration

@Singleton
internal class CacheManagerImpl(
    private val dao: CacheTimestampDao,
    private val currentTimestampProvider: () -> Long,
    private val cacheDurationProvider: () -> Duration,
    private val dispatcher: CoroutineDispatchers,
) : CacheManager {

    override suspend fun isCacheActual(key: String): Boolean = withContext(dispatcher.io) {
        val since = currentTimestampProvider() - cacheDurationProvider().inWholeMilliseconds
        dao.selectKeysWithNewerTimestamp(since).contains(key)
    }

    override suspend fun updateCacheTimestamp(key: String) = withContext(dispatcher.io) {
        dao.insertOrReplace(CacheTimestampEntity(key, currentTimestampProvider()))
    }
}
