package com.example.home_hackathon.repository.di

import com.example.home_hackathon.repository.EventRepository
import org.koin.dsl.module

val repositoryModule = module {
    single { EventRepository(get()) }
}