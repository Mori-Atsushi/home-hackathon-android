package com.example.home_hackathon.ui.binding_adapter

import android.annotation.SuppressLint
import android.view.MotionEvent
import android.view.View
import androidx.databinding.BindingAdapter

object ViewBindingAdapter {
    @SuppressLint("ClickableViewAccessibility")
    @BindingAdapter("isTouch")
    @JvmStatic
    fun setIsTouchListener(view: View, listener: IsTouchListener) {
        view.setOnTouchListener { _, motionEvent ->
            when (motionEvent?.action) {
                MotionEvent.ACTION_DOWN -> listener.onChange(true)
                MotionEvent.ACTION_UP -> listener.onChange(false)
            }
            false
        }
    }

    interface IsTouchListener {
        fun onChange(isTouch: Boolean)
    }
}
