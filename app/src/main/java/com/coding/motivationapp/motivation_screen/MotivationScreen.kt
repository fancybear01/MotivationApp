package com.coding.motivationapp.motivation_screen

import android.content.Context
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.snapping.SnapPosition
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.coding.motivationapp.R
import org.koin.androidx.compose.koinViewModel

@Composable
fun MotivationScreen(
    context: Context,
    viewModel: MotivationViewModel = koinViewModel()
    ) {
    val state by viewModel.state.collectAsState()
    Scaffold { padding ->
        Column (
            modifier = Modifier
                .fillMaxSize()
                .clickable { viewModel.refreshQuote() }
                .padding(padding),
            verticalArrangement = Arrangement.Center
        ) {
            when {
                state.isLoading -> {
                    //CircularProgressIndicator()
                }

                state.error != null -> {
                    Text(
                        text = "Ошибка: ${state.error}",
                        color = MaterialTheme.colorScheme.error
                    )
                }

                else -> {
                    Text(
                        text = state.currentQuote,
                        fontSize = 18.sp,
                        fontStyle = FontStyle.Italic,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.animateContentSize()
                    )
                }
            }
        }
    }

}