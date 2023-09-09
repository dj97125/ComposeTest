package com.danielcaballero.composetest.di

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

@HiltAndroidApp
class CountryApp : Application() {


    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(Level.ERROR)
            androidContext(this@CountryApp)
            modules(listOf(appModule, utilitiesModule, viewModelModule, dataBaseModule))
        }
    }
}