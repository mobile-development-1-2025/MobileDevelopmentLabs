package com.dak0ta.learnity.feature.settings.presentation.di

import androidx.lifecycle.ViewModel
import com.dak0ta.learnity.core.di.ViewModelKey
import com.dak0ta.learnity.feature.settings.presentation.SettingsViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class SettingsPresentationModule {

    @Binds
    @IntoMap
    @ViewModelKey(SettingsViewModel::class)
    internal abstract fun bindSettingsViewModel(viewModel: SettingsViewModel): ViewModel
}
