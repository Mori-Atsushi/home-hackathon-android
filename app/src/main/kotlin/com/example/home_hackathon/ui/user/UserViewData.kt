package com.example.home_hackathon.ui.user

import com.example.home_hackathon.model.Sound

data class UserViewData(
    val id: String,
    val sounds: List<Int> = listOf()
) {
    fun updated(sound: Sound): UserViewData {
        val sounds = if (sound.isDown) {
            sounds + sound.soundID
        } else {
            sounds.filter { it != sound.soundID }
        }
        return copy(sounds = sounds)
    }
}
