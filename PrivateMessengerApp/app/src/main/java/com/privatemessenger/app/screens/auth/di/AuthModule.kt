package com.privatemessenger.app.screens.auth.di

import com.privatemessenger.app.screens.auth.data.AuthCloudDataSource
import com.privatemessenger.app.screens.auth.data.AuthCloudDataSourceImpl
import com.privatemessenger.app.screens.auth.data.AuthLocalDataSource
import com.privatemessenger.app.screens.auth.data.AuthLocalDataSourceImpl
import com.privatemessenger.app.screens.auth.data.AuthRepository
import com.privatemessenger.app.screens.auth.data.AuthRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class AuthModule {
    @Binds
    abstract fun bindAuthCloudDataSource(
        authCloudDataSourceImpl: AuthCloudDataSourceImpl
    ): AuthCloudDataSource

    @Binds
    abstract fun bindAuthLocalDataSource(
        authLocalDataSourceImpl: AuthLocalDataSourceImpl
    ): AuthLocalDataSource

    @Binds
    abstract fun bindAuthRepository(
        authRepositoryImpl: AuthRepositoryImpl
    ): AuthRepository
}