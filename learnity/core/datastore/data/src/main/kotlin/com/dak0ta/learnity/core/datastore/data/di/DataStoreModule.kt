package com.dak0ta.learnity.core.datastore.data.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import androidx.datastore.core.Serializer
import androidx.datastore.core.handlers.ReplaceFileCorruptionHandler
import androidx.datastore.dataStoreFile
import com.dak0ta.learnity.core.datastore.data.repository.UserPreferencesRepository
import com.dak0ta.learnity.core.datastore.data.repository.UserPreferencesRepositoryImpl
import com.dak0ta.learnity.core.datastore.data.serializer.UserPreferencesSerializer
import com.dak0ta.learnity.core.datastore.proto.UserPreferences
import dagger.Binds
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(includes = [DataStoreUseCaseModule::class])
abstract class DataStoreModule {

    @Binds
    @Singleton
    internal abstract fun bindUserPreferencesRepository(
        impl: UserPreferencesRepositoryImpl,
    ): UserPreferencesRepository

    companion object {

        @Provides
        @JvmStatic
        @Singleton
        fun provideUserPreferencesDataStore(
            context: Context,
            serializer: Serializer<UserPreferences>,
        ): DataStore<UserPreferences> = DataStoreFactory.create(
            serializer = serializer,
            corruptionHandler = ReplaceFileCorruptionHandler { UserPreferences.getDefaultInstance() },
            produceFile = { context.dataStoreFile("user_prefs.pb") },
        )

        @Provides
        @JvmStatic
        @Singleton
        fun provideUserPreferencesSerializer(): Serializer<UserPreferences> = UserPreferencesSerializer()
    }
}
