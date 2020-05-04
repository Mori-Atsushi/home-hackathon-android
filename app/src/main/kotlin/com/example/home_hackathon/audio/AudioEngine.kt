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

    fun setToneOn(isToneOn: Boolean) {
        setToneOn(engineHandle, isToneOn)
    }

    private external fun start(): Long
    private external fun setToneOn(engineHandle: Long, isToneOn: Boolean)
}
