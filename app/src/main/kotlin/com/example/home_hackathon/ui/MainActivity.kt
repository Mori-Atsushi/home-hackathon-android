package com.example.home_hackathon.ui

import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.view.MotionEvent
import androidx.appcompat.app.AppCompatActivity
import org.koin.android.ext.android.inject
import kotlin.properties.Delegates

class MainActivity : AppCompatActivity() {
    companion object {
        init {
            System.loadLibrary("native-lib")
        }
    }

    private val viewModel: MainViewModel by inject()
    private var engineHandle by Delegates.notNull<Long>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        engineHandle = start()
        viewModel
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        when (event?.action) {
            MotionEvent.ACTION_DOWN -> setToneOn(engineHandle, true)
            MotionEvent.ACTION_UP -> setToneOn(engineHandle, false)
        }
        return super.onTouchEvent(event)
    }

    private external fun start(): Long
    private external fun setToneOn(engineHandle: Long, isToneOn: Boolean)
}