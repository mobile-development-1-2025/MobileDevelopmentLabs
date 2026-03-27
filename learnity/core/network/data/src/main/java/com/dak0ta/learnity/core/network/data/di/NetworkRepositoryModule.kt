package com.dak0ta.learnity.core.network.data.di

import com.dak0ta.learnity.core.coroutine.CoroutineDispatchers
import com.dak0ta.learnity.core.network.data.api.service.AuthService
import com.dak0ta.learnity.core.network.data.api.service.QuoteService
import com.dak0ta.learnity.core.network.data.api.service.UserService
import com.dak0ta.learnity.core.network.data.network.SafeApiCall
import com.dak0ta.learnity.core.network.data.repository.AuthRemoteRepositoryImpl
import com.dak0ta.learnity.core.network.data.repository.QuotesRemoteRepositoryImpl
import com.dak0ta.learnity.core.network.data.repository.UserRemoteRepositoryImpl
import com.dak0ta.learnity.core.network.domain.repository.AuthRemoteRepository
import com.dak0ta.learnity.core.network.domain.repository.QuotesRemoteRepository
import com.dak0ta.learnity.core.network.domain.repository.UserRemoteRepository
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
object NetworkRepositoryModule {

    @Provides
    @Singleton
    fun provideAuthRemoteRepository(
        retrofit: Retrofit,
        dispatchers: CoroutineDispatchers,
    ): AuthRemoteRepository {
        val service = retrofit.create(AuthService::class.java)
        val safeApiCall = SafeApiCall(dispatchers)
        return AuthRemoteRepositoryImpl(service, safeApiCall)
    }

    @Provides
    @Singleton
    fun provideUserRemoteRepository(
        retrofit: Retrofit,
        dispatchers: CoroutineDispatchers,
    ): UserRemoteRepository {
        val service = retrofit.create(UserService::class.java)
        val safeApiCall = SafeApiCall(dispatchers)
        return UserRemoteRepositoryImpl(service, safeApiCall)
    }

    @Provides
    @Singleton
    fun provideQuotesRemoteRepository(
        retrofit: Retrofit,
        dispatchers: CoroutineDispatchers,
    ): QuotesRemoteRepository {
        val service = retrofit.create(QuoteService::class.java)
        val safeApiCall = SafeApiCall(dispatchers)
        return QuotesRemoteRepositoryImpl(service, safeApiCall)
    }
}
