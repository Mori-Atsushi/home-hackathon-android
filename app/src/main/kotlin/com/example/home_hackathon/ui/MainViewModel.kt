package com.example.home_hackathon.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.home_hackathon.audio.AudioEngine
import com.example.home_hackathon.model.Event
import com.example.home_hackathon.model.Sound
import com.example.home_hackathon.repository.EventRepository
import com.example.home_hackathon.ui.user.UserViewData
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
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

    private val soundFlow = receiveFlow.filterIsInstance<Event.SoundEvent>()
    private val userFlow = receiveFlow.filterIsInstance<Event.UserEvent>()

    private val usersChannel: ConflatedBroadcastChannel<List<UserViewData>> =
        ConflatedBroadcastChannel(listOf())
    val users: Flow<List<UserViewData>> = usersChannel.asFlow()

    init {
        soundFlow
            .onEach { audioEngine.setToneOn(it.sound.soundID, it.sound.isDown) }
            .launchIn(viewModelScope)

        userFlow
            .map { event ->
                val lastValue = usersChannel.value
                event.userIDs.map { id ->
                    lastValue.find { viewData ->
                        viewData.id == id
                    } ?: UserViewData(id)
                }
            }
            .onEach {
                usersChannel.send(it)
            }
            .launchIn(viewModelScope)

        soundFlow
            .map { event ->
                usersChannel.value.map { viewData ->
                    if (event.userID == viewData.id) {
                        viewData.updated(event.sound)
                    } else {
                        viewData
                    }
                }
            }
            .onEach {
                usersChannel.send(it)
            }
            .launchIn(viewModelScope)
    }

    fun touch(key: Int, isDown: Boolean) {
        val sound = Sound(soundID = key, isDown = isDown)
        viewModelScope.launch {
            inputChannel.send(sound)
        }
    }
}
