package com.example.home_hackathon.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.home_hackathon.repository.EventRepository
import kotlinx.coroutines.launch

class MainViewModel(
    private val repository: EventRepository
) : ViewModel() {
    init {
        viewModelScope.launch {
            repository.ping()
        }
    }
}