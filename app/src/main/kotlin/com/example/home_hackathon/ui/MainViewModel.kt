package com.example.home_hackathon.ui

import androidx.lifecycle.ViewModel
import com.example.home_hackathon.audio.AudioEngine
import com.example.home_hackathon.repository.EventRepository

class MainViewModel(
    private val repository: EventRepository,
    private val audioEngine: AudioEngine
) : ViewModel() {
    fun touchDown() {
        audioEngine.setToneOn(true)
    }

    fun touchUp() {
        audioEngine.setToneOn(false)
    }
}