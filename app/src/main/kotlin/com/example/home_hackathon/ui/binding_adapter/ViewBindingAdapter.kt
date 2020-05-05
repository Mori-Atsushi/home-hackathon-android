package com.example.home_hackathon.ui.binding_adapter

import android.annotation.SuppressLint
import android.graphics.drawable.TransitionDrawable
import android.view.MotionEvent
import android.view.View
import androidx.databinding.BindingAdapter

object ViewBindingAdapter {
    private const val ANIMATION_DURATION_MILLIS = 100

    @SuppressLint("ClickableViewAccessibility")
    @BindingAdapter("isTouch")
    @JvmStatic
    fun setIsTouchListener(view: View, listener: IsTouchListener) {
        view.setOnTouchListener { _, motionEvent ->
            when (motionEvent?.action) {
                MotionEvent.ACTION_DOWN -> listener.onChange(true)
                MotionEvent.ACTION_CANCEL,
                MotionEvent.ACTION_UP -> listener.onChange(false)
            }
            false
        }
    }

    @BindingAdapter("transition_background")
    @JvmStatic
    fun setIsTransitionBackground(view: View, value: Boolean) {
        val drawable = view.background as? TransitionDrawable ?: return
        drawable.isCrossFadeEnabled = true
        if (value) {
            drawable.startTransition(ANIMATION_DURATION_MILLIS)
        } else {
            drawable.startTransition(0)
            drawable.reverseTransition(ANIMATION_DURATION_MILLIS)
        }
    }

    interface IsTouchListener {
        fun onChange(isTouch: Boolean)
    }
}
