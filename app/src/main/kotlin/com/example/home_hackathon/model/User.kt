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
