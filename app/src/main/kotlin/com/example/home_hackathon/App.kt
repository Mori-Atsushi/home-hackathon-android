package com.example.home_hackathon

import android.app.Application
import com.example.home_hackathon.network.di.networkModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {
    companion object {
        private val modules = listOf(
            networkModule
        )
    }

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@App)
            modules(modules)
        }
    }
}