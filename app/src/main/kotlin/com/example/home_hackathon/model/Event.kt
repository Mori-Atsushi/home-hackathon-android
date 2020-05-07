package com.example.home_hackathon.model

sealed class Event {
    data class SoundEvent(
        val sound: Sound,
        val userID: String
    ) : Event()

    data class UserEvent(
        val userIDs: List<String>
    ) : Event()
}
