package com.example.home_hackathon.model

data class Keyboard(
    val keys: List<Key> = List(
        KEY_NUM
    ) { Key() }
) {
    companion object {
        const val KEY_NUM = 104
        const val START_KEY = 24
    }

    data class Key(
        val enableUserIds: List<String> = emptyList()
    ) {
        fun updated(event: Event.SoundEvent): Key {
            val ids = if (event.sound.isDown) {
                enableUserIds + event.userID
            } else {
                enableUserIds.filter { it != event.userID }
            }
            return Key(ids)
        }

        val isEnable: Boolean = enableUserIds.isNotEmpty()
        val isOneUser: Boolean = enableUserIds.size == 1
    }

    fun updated(event: Event.SoundEvent): Keyboard {
        val keys = keys.mapIndexed { index, key ->
            if (index + START_KEY == event.sound.soundID) {
                key.updated(event)
            } else {
                key
            }
        }
        return copy(keys = keys)
    }

    operator fun get(key: Int): Key {
        return keys[key - START_KEY]
    }

    fun subList(fromKey: Int, toKey: Int): List<Key> {
        return keys.subList(fromKey - START_KEY, toKey - START_KEY)
    }
}
