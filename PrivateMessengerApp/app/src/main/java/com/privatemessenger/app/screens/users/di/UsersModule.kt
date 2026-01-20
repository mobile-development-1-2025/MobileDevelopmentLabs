package com.privatemessenger.app.screens.users.di

import com.privatemessenger.app.screens.users.data.UsersCloudDataSource
import com.privatemessenger.app.screens.users.data.UsersCloudDataSourceImpl
import com.privatemessenger.app.screens.users.data.UsersRepository
import com.privatemessenger.app.screens.users.data.UsersRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class UsersModule {
    @Binds
    abstract fun bindUsersCloudDataSource(
        authCloudDataSourceImpl: UsersCloudDataSourceImpl
    ): UsersCloudDataSource

    @Binds
    abstract fun bindUsersRepository(
        authRepositoryImpl: UsersRepositoryImpl
    ): UsersRepository
}