package com.example.home_hackathon.ui.ext

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@ExperimentalCoroutinesApi
inline fun <T> AppCompatActivity.bind(
    source: Flow<T>,
    crossinline action: (T) -> Unit
) {
    source.onEach { action.invoke(it) }
        .launchIn(lifecycleScope)
}