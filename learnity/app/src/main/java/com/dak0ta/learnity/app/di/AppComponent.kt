package com.dak0ta.learnity.app.di

import android.app.Application
import androidx.lifecycle.ViewModelProvider
import com.dak0ta.learnity.app.worker.QuotesWorkerFactory
import com.dak0ta.learnity.core.coroutine.di.CoroutineModule
import com.dak0ta.learnity.core.database.data.di.DatabaseModule
import com.dak0ta.learnity.core.datastore.data.di.DataStoreModule
import com.dak0ta.learnity.core.datastore.domain.usecase.theme.GetAppThemeUseCase
import com.dak0ta.learnity.core.datastore.domain.usecase.theme.ObserveAppThemeUseCase
import com.dak0ta.learnity.core.di.ViewModelFactoryModule
import com.dak0ta.learnity.core.network.data.di.NetworkModule
import com.dak0ta.learnity.feature.authorization.data.di.AuthorizationDataModule
import com.dak0ta.learnity.feature.authorization.presentation.di.AuthorizationPresentationModule
import com.dak0ta.learnity.feature.home.data.di.HomeDataModule
import com.dak0ta.learnity.feature.home.presentation.di.HomePresentationModule
import com.dak0ta.learnity.feature.profile.data.di.ProfileDataModule
import com.dak0ta.learnity.feature.profile.presentation.di.ProfilePresentationModule
import com.dak0ta.learnity.feature.settings.presentation.di.SettingsPresentationModule
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AppModule::class,
        WorkerModule::class,
        CoroutineModule::class,
        DatabaseModule::class,
        DataStoreModule::class,
        NetworkModule::class,
        AuthorizationDataModule::class,
        AuthorizationPresentationModule::class,
        HomeDataModule::class,
        HomePresentationModule::class,
        ProfileDataModule::class,
        ProfilePresentationModule::class,
        SettingsPresentationModule::class,
        ViewModelFactoryModule::class,
    ],
)
interface AppComponent {

    fun quotesWorkerFactory(): QuotesWorkerFactory
    fun viewModelFactory(): ViewModelProvider.Factory
    fun observeAppThemeUseCase(): ObserveAppThemeUseCase
    fun getAppThemeUseCase(): GetAppThemeUseCase

    @Component.Factory
    interface Factory {

        fun create(
            @BindsInstance application: Application,
        ): AppComponent
    }
}
