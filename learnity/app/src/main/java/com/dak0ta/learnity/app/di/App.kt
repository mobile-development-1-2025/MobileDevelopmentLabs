package com.dak0ta.learnity.app.di

import android.app.Application
import android.content.Context
import androidx.work.Configuration
import com.dak0ta.learnity.app.notification.QuotesNotificationChannel

class App : Application(), Configuration.Provider {

    val appComponent: AppComponent by lazy {
        DaggerAppComponent.factory().create(
            application = this,
        )
    }

    override val workManagerConfiguration: Configuration
        get() = Configuration.Builder()
            .setWorkerFactory(appComponent.quotesWorkerFactory())
            .build()

    override fun onCreate() {
        super.onCreate()
        QuotesNotificationChannel.create(this)
    }
}

val Context.appComponent: AppComponent
    get() = when (this) {
        is App -> appComponent
        else -> this.applicationContext.appComponent
    }
