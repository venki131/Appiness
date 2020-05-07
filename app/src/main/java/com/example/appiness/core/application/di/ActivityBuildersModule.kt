package com.example.appiness.core.application.di

import com.danbro.delivery.core.application.di.login.BakerScope
import com.danbro.delivery.core.application.di.login.ViewModelsModule
import com.example.appiness.core.application.di.bakers.BakersModule
import com.example.appiness.presentation.view.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuildersModule {
    @BakerScope
    @ContributesAndroidInjector(
        modules = [
            ViewModelsModule::class,
            BakersModule::class
        ]
    )
    abstract fun contributeLoginActivity(): MainActivity
}