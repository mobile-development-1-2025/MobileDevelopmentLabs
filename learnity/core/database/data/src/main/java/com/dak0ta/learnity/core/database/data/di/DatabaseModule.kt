package com.dak0ta.learnity.core.database.data.di

import android.content.Context
import androidx.room.Room
import com.dak0ta.learnity.core.coroutine.CoroutineDispatchers
import com.dak0ta.learnity.core.database.data.cache.CacheManagerImpl
import com.dak0ta.learnity.core.database.data.db.AppDatabase
import com.dak0ta.learnity.core.database.data.repository.QuotesLocalRepositoryImpl
import com.dak0ta.learnity.core.database.data.repository.UserLocalRepositoryImpl
import com.dak0ta.learnity.core.database.domain.cache.CacheManager
import com.dak0ta.learnity.core.database.domain.repository.QuotesLocalRepository
import com.dak0ta.learnity.core.database.domain.repository.UserLocalRepository
import dagger.Module
import dagger.Provides
import java.util.concurrent.Executors
import javax.inject.Named
import javax.inject.Singleton
import kotlin.time.Duration
import kotlin.time.Duration.Companion.hours

@Module
object DatabaseModule {

    private const val DEFAULT_DB_NAME = "learnity.db"

    @Provides
    @Named("dbName")
    @Singleton
    fun provideDbName(): String = DEFAULT_DB_NAME

    @Provides
    @Named("currentTimestampProvider")
    fun provideCurrentTimestampProvider(): () -> Long = { System.currentTimeMillis() }

    @Provides
    @Named("cacheDurationProvider")
    fun provideCacheDurationProvider(): () -> Duration = { 12.hours }

    @Provides
    @Singleton
    fun provideAppDatabase(context: Context): AppDatabase =
        Room.databaseBuilder(context.applicationContext, AppDatabase::class.java, DEFAULT_DB_NAME)
            .setQueryExecutor(Executors.newSingleThreadExecutor())
            .build()

    @Provides
    fun provideUserLocalRepository(db: AppDatabase): UserLocalRepository =
        UserLocalRepositoryImpl(db.userDao())

    @Provides
    fun provideQuotesLocalRepository(db: AppDatabase): QuotesLocalRepository =
        QuotesLocalRepositoryImpl(db.quotesDao())

    @Provides
    @Singleton
    fun provideCacheManager(
        db: AppDatabase,
        @Named("currentTimestampProvider") currentTs: () -> Long,
        @Named("cacheDurationProvider") cacheDuration: () -> Duration,
        dispatchers: CoroutineDispatchers,
    ): CacheManager = CacheManagerImpl(
        dao = db.cacheTimestampDao(),
        currentTimestampProvider = currentTs,
        cacheDurationProvider = cacheDuration,
        dispatcher = dispatchers,
    )
}
