package com.example.home_hackathon.audio

class AudioEngine {
    companion object {
        init {
            System.loadLibrary("native-lib")
        }
    }

    private val engineHandle: Long

    init {
        engineHandle = start()
    }

    fun setToneOn(key: Int, isToneOn: Boolean) {
        setToneOn(engineHandle, key, isToneOn)
    }

    private external fun start(): Long
    private external fun setToneOn(engineHandle: Long, key: Int, isToneOn: Boolean)
}
