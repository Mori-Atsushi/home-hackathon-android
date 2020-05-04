package com.example.home_hackathon.ui.di

import com.example.home_hackathon.ui.MainViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val uiModule = module {
    viewModel { MainViewModel(get()) }
}