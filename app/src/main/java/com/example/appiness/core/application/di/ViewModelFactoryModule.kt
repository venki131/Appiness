package com.example.appiness.core.application.di

import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module


@Module
abstract class ViewModelFactoryModule {
    @Binds
    abstract fun bindsFactory(modelProviderFactory: ViewModelProviderFactory): ViewModelProvider.Factory
}