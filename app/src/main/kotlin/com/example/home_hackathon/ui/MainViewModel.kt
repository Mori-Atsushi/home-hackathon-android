package com.example.home_hackathon.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.home_hackathon.audio.AudioEngine
import com.example.home_hackathon.repository.EventRepository
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class MainViewModel(
    private val repository: EventRepository,
    private val audioEngine: AudioEngine
) : ViewModel() {
    private val channel = Channel<Boolean>(Channel.BUFFERED)

    init {
        repository.event(channel.consumeAsFlow())
            .onEach { audioEngine.setToneOn(it) }
            .launchIn(viewModelScope)
    }

    fun touchDown() {
        viewModelScope.launch {
            channel.send(true)
        }
    }

    fun touchUp() {
        viewModelScope.launch {
            channel.send(false)
        }
    }
}