package com.coding.motivationapp.motivation_screen

data class QuoteState(
    val currentQuote: String = "",
    val isLoading: Boolean = false,
    val error: String? = null
)