package com.danbro.delivery.core.application.di.login

import androidx.lifecycle.ViewModel
import com.example.appiness.core.application.di.ViewModelKey
import com.example.appiness.presentation.viewmodels.MainViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelsModule {
    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    abstract fun bindLoginViewModel(loginViewModel: MainViewModel): ViewModel
}