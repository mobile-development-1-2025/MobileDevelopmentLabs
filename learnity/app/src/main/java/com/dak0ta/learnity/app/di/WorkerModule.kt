package com.dak0ta.learnity.app.di

import android.content.Context
import com.dak0ta.learnity.app.notification.QuotesNotificationManager
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object WorkerModule {

    @Provides
    @Singleton
    fun provideQuotesNotificationManager(context: Context): QuotesNotificationManager =
        QuotesNotificationManager(context)
}
