package com.dak0ta.learnity.feature.authorization.presentation.di

import androidx.lifecycle.ViewModel
import com.dak0ta.learnity.core.di.ViewModelKey
import com.dak0ta.learnity.feature.authorization.presentation.AuthorizationViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class AuthorizationPresentationModule {

    @Binds
    @IntoMap
    @ViewModelKey(AuthorizationViewModel::class)
    internal abstract fun bindAuthorizationViewModel(viewModel: AuthorizationViewModel): ViewModel
}
