package com.coding.motivationapp.di

import com.coding.motivationapp.motivation_screen.MotivationViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val AppModule = module {
    viewModel { MotivationViewModel(get()) }
}