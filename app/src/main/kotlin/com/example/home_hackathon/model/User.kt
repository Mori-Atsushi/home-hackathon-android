package com.example.home_hackathon.model

data class User(
    val id: String,
    val sounds: List<Int> = listOf()
) {
    fun updated(sound: Sound): User {
        val sounds = if (sound.isDown) {
            sounds + sound.soundID
        } else {
            sounds.filter { it != sound.soundID }
        }
        return copy(sounds = sounds)
    }
}

fun List<User>.updated(event: Event): List<User> {
    return when (event) {
        is Event.SoundEvent -> updated(event)
        is Event.UserEvent -> updated(event)
    }
}

private fun List<User>.updated(event: Event.UserEvent): List<User> {
    return event.userIDs.map { id ->
        this.find { viewData ->
            viewData.id == id
        } ?: User(id)
    }
}

private fun List<User>.updated(event: Event.SoundEvent): List<User> {
    return this.map { viewData ->
        if (event.userID == viewData.id) {
            viewData.updated(event.sound)
        } else {
            viewData
        }
    }
}
