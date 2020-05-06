package com.example.appiness.core.application.di

import com.danbro.delivery.core.application.di.login.LoginScope
import com.danbro.delivery.core.application.di.login.LoginViewModelsModule
import com.example.appiness.core.application.di.bakers.BakersModule
import com.example.appiness.presentation.view.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuildersModule {
    @LoginScope
    @ContributesAndroidInjector(
        modules = [
            LoginViewModelsModule::class,
            BakersModule::class
        ]
    )
    abstract fun contributeLoginActivity(): MainActivity
}