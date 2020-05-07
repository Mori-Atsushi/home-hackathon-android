package com.example.home_hackathon.ui.ext

import android.view.View

fun View.setVisibleGone(isVisible: Boolean) {
    visibility = if (isVisible) {
        View.VISIBLE
    } else {
        View.GONE
    }
}
