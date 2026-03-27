package com.dak0ta.learnity.core.database.domain.cache

interface CacheManager {

    suspend fun isCacheActual(key: String): Boolean
    suspend fun updateCacheTimestamp(key: String)
}
