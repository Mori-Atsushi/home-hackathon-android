package com.example.home_hackathon.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.home_hackathon.audio.AudioEngine
import com.example.home_hackathon.model.Event
import com.example.home_hackathon.model.Sound
import com.example.home_hackathon.repository.EventRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
@FlowPreview
class MainViewModel(
    repository: EventRepository,
    private val audioEngine: AudioEngine
) : ViewModel() {
    private val inputChannel: Channel<Sound> = Channel(Channel.BUFFERED)
    private val receiveFlow: Flow<Event> = repository.event(inputChannel.consumeAsFlow())
        .broadcastIn(viewModelScope)
        .asFlow()

    init {
        receiveFlow
            .filterIsInstance<Event.SoundEvent>()
            .onEach { audioEngine.setToneOn(it.sound.soundID, it.sound.isDown) }
            .launchIn(viewModelScope)
    }

    fun touch(key: Int, isDown: Boolean) {
        val sound = Sound(soundID = key, isDown = isDown)
        viewModelScope.launch {
            inputChannel.send(sound)
        }
    }
}
