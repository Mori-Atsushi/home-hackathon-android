package com.example.home_hackathon.ui.keyboard

import com.airbnb.epoxy.TypedEpoxyController
import com.example.home_hackathon.keyboard

class KeyboardController(
    private val listener: KeyboardListener
) : TypedEpoxyController<KeyboardViewData>() {
    companion object {
        private const val START_KEY = 24
        private const val END_KEY = 96
        private const val KEY_STEP = 12
    }

    override fun buildModels(data: KeyboardViewData?) {
        for (i in START_KEY..END_KEY step KEY_STEP) {
            val keys = data?.subList(i, i + KEY_STEP + 1)?.toTypedArray()
            keyboard {
                id(i)
                startKey(i)
                keys(keys)
                listener(listener)
            }
        }
    }
}
