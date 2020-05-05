package com.example.home_hackathon.ui.keyboard

import com.airbnb.epoxy.EpoxyController
import com.example.home_hackathon.keyboard

class KeyboardController(
    private val listener: KeyboardListener
) : EpoxyController() {
    companion object {
        private const val START_KEY = 24
        private const val END_KEY = 108
        private const val KEY_STEP = 12
    }

    override fun buildModels() {
        for (i in START_KEY..END_KEY step KEY_STEP) {
            keyboard {
                id(i)
                startKey(i)
                listener(listener)
            }
        }
    }
}