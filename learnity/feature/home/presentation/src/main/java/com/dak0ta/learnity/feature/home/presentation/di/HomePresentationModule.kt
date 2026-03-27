package com.dak0ta.learnity.feature.home.presentation.di

import androidx.lifecycle.ViewModel
import com.dak0ta.learnity.core.di.ViewModelKey
import com.dak0ta.learnity.feature.home.presentation.HomeViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class HomePresentationModule {

    @Binds
    @IntoMap
    @ViewModelKey(HomeViewModel::class)
    internal abstract fun bindHomeViewModel(viewModel: HomeViewModel): ViewModel
}
