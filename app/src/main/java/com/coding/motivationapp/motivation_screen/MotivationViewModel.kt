package com.coding.motivationapp.motivation_screen

import android.app.Application
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.coding.motivationapp.R
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class MotivationViewModel(application: Application) : ViewModel() {
    private val _state = MutableStateFlow(QuoteState())
    val state = _state

    private val quotes = application.resources.getStringArray(R.array.motivational_quotes).toList()

    init {
        getRandomQuote()
    }

    fun getRandomQuote() {
        _state.value = _state.value.copy(
            currentQuote = quotes.random(),
            isLoading = false,
            error = null
        )
    }

    fun refreshQuote() {
        _state.value = _state.value.copy(isLoading = true)
        viewModelScope.launch {
            delay(500)
            getRandomQuote()
        }
    }
}