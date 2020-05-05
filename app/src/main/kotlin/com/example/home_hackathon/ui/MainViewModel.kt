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
    companion object {
        private const val START_PAGE = 3
        private const val MAX_PAGE = 6
    }

    private val inputChannel: Channel<Sound> = Channel(Channel.BUFFERED)
    private val receiveFlow: Flow<Event> = repository.event(inputChannel.consumeAsFlow())
        .broadcastIn(viewModelScope)
        .asFlow()

    private val usersChannel: ConflatedBroadcastChannel<List<UserViewData>> =
        ConflatedBroadcastChannel(listOf())
    val users: Flow<List<UserViewData>> = usersChannel.asFlow()

    private val currentPageChannel: ConflatedBroadcastChannel<Int> =
        ConflatedBroadcastChannel(START_PAGE)
    val currentPage: Flow<Int> = currentPageChannel.asFlow()
    val currentPageValue: Int get() = currentPageChannel.value

    val isEnabledLeft: Flow<Boolean> = currentPage.map {
        it > 0
    }.distinctUntilChanged()
    val isEnableRight: Flow<Boolean> = currentPage.map {
        it < MAX_PAGE
    }.distinctUntilChanged()

    init {
        receiveFlow
            .filterIsInstance<Event.SoundEvent>()
            .onEach { audioEngine.setToneOn(it.sound.soundID, it.sound.isDown) }
            .launchIn(viewModelScope)

        receiveFlow.scan(emptyList<UserViewData>()) { acc, value ->
            acc.updated(value)
        }.onEach {
            usersChannel.send(it)
        }.launchIn(viewModelScope)
    }

    fun touch(key: Int, isDown: Boolean) {
        val sound = Sound(soundID = key, isDown = isDown)
        viewModelScope.launch {
            inputChannel.send(sound)
        }
    }

    fun leftPage() {
        val value = currentPageValue
        if (value <= 0) return

        viewModelScope.launch {
            currentPageChannel.send(value - 1)
        }
    }

    fun rightPage() {
        val value = currentPageValue
        if (value >= MAX_PAGE) return

        viewModelScope.launch {
            currentPageChannel.send(value + 1)
        }
    }

    private fun List<UserViewData>.updated(event: Event): List<UserViewData> {
        return when (event) {
            is Event.SoundEvent -> updated(event)
            is Event.UserEvent -> updated(event)
        }
    }

    private fun List<UserViewData>.updated(event: Event.UserEvent): List<UserViewData> {
        return event.userIDs.map { id ->
            this.find { viewData ->
                viewData.id == id
            } ?: UserViewData(id)
        }
    }

    private fun List<UserViewData>.updated(event: Event.SoundEvent): List<UserViewData> {
        return this.map { viewData ->
            if (event.userID == viewData.id) {
                viewData.updated(event.sound)
            } else {
                viewData
            }
        }
    }
}
