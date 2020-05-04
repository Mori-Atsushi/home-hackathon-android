package com.example.home_hackathon

import android.app.Application
import com.example.home_hackathon.network.di.networkModule
import com.example.home_hackathon.repository.di.repositoryModule
import com.example.home_hackathon.ui.di.uiModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {
    companion object {
        private val modules = listOf(
            uiModule,
            repositoryModule,
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