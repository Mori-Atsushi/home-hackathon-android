package com.example.home_hackathon.audio.di

import com.example.home_hackathon.audio.AudioEngine
import org.koin.dsl.module

val audioModule = module {
    single { AudioEngine() }
}
