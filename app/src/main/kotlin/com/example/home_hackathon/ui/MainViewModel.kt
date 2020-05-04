package com.example.home_hackathon.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.home_hackathon.audio.AudioEngine
import com.example.home_hackathon.pb.App.Event
import com.example.home_hackathon.repository.EventRepository
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class MainViewModel(
    repository: EventRepository,
    private val audioEngine: AudioEngine
) : ViewModel() {
    private val channel = Channel<Event>(Channel.BUFFERED)

    init {
        repository.event(channel.consumeAsFlow())
            .onEach { audioEngine.setToneOn(it.soundId, it.isDown) }
            .launchIn(viewModelScope)
    }

    fun touch(key: Int, isDown: Boolean) {
        val event = Event.newBuilder().also {
            it.soundId = key
            it.isDown = isDown
        }.build()

        viewModelScope.launch {
            channel.send(event)
        }
    }
}
