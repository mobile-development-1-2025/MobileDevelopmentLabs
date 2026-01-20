package com.privatemessenger.app.screens.chatList.di

import com.privatemessenger.app.screens.chatList.data.ChatListCloudDataSource
import com.privatemessenger.app.screens.chatList.data.ChatListCloudDataSourceImpl
import com.privatemessenger.app.screens.chatList.data.ChatListRepository
import com.privatemessenger.app.screens.chatList.data.ChatListRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class ChatListModule {
    @Binds
    abstract fun bindChatCloudDataSource(
        authCloudDataSourceImpl: ChatListCloudDataSourceImpl
    ): ChatListCloudDataSource

    @Binds
    abstract fun bindChatRepository(
        authRepositoryImpl: ChatListRepositoryImpl
    ): ChatListRepository
}