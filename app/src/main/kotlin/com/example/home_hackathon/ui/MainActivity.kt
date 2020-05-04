package com.example.home_hackathon.ui

import android.view.MotionEvent
import androidx.appcompat.app.AppCompatActivity
import org.koin.android.ext.android.inject

class MainActivity : AppCompatActivity() {
    private val viewModel: MainViewModel by inject()

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        when (event?.action) {
            MotionEvent.ACTION_DOWN -> viewModel.touchDown()
            MotionEvent.ACTION_UP -> viewModel.touchUp()
        }
        return super.onTouchEvent(event)
    }
}