package com.coding.motivationapp

import android.app.Application
import com.coding.motivationapp.di.AppModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class MotivationApp: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MotivationApp)
            androidLogger()
            modules(AppModule)
        }
    }
}