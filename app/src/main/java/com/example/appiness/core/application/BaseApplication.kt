package com.example.appiness.core.application

import com.example.appiness.core.application.di.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication

class BaseApplication : DaggerApplication() {

    init {
        instance = this
    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerAppComponent.builder().application(this).build()
    }

    override fun onCreate() {
        super.onCreate()
    }

    companion object {
        private var instance: BaseApplication? = null

        fun getInstance(): BaseApplication {
            return instance as BaseApplication
        }
    }
}