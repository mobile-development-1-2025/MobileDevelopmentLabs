package com.privatemessenger.app.screens.chat.di

import com.privatemessenger.app.screens.chat.data.ChatCloudDataSource
import com.privatemessenger.app.screens.chat.data.ChatCloudDataSourceImpl
import com.privatemessenger.app.screens.chat.data.ChatLocalDataSource
import com.privatemessenger.app.screens.chat.data.ChatLocalDataSourceImpl
import com.privatemessenger.app.screens.chat.data.ChatRepository
import com.privatemessenger.app.screens.chat.data.ChatRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class ChatModule {
    @Binds
    abstract fun bindChatCloudDataSource(
        authCloudDataSourceImpl: ChatCloudDataSourceImpl
    ): ChatCloudDataSource

    @Binds
    abstract fun bindChatLocalDataSource(
        authLocalDataSourceImpl: ChatLocalDataSourceImpl
    ): ChatLocalDataSource

    @Binds
    abstract fun bindChatRepository(
        authRepositoryImpl: ChatRepositoryImpl
    ): ChatRepository
}