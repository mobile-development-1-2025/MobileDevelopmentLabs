package com.privatemessenger.app.common.di

import android.content.Context
import androidx.room.Room
import com.privatemessenger.app.common.data.AppDatabase
import com.privatemessenger.app.screens.chat.dao.ChatDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CommonModule {
    @Singleton
    @Provides
    fun provideAppDatabase(
        @ApplicationContext context: Context
    ): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java, "chat-db"
        ).fallbackToDestructiveMigration().build()
    }

    @Singleton
    @Provides
    fun provideChatDao(
        appDatabase: AppDatabase
    ): ChatDao {
        return appDatabase.chatDao()
    }
}