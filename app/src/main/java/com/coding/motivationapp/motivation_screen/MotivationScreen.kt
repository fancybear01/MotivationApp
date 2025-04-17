package com.coding.motivationapp.motivation_screen

import android.content.Context
import androidx.compose.animation.animateColor
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.snapping.SnapPosition
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.RoundRect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.PathMeasure
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.coding.motivationapp.R
import org.koin.androidx.compose.koinViewModel
import kotlin.io.path.Path
import kotlin.random.Random
import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.*

import androidx.compose.ui.graphics.*
import androidx.compose.ui.layout.onSizeChanged


@Composable
fun MotivationScreen(
    context: Context,
    viewModel: MotivationViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsState()
    val infiniteTransition = rememberInfiniteTransition()

    // Анимация границы
    val borderColor by infiniteTransition.animateColor(
        initialValue = Color.White.copy(alpha = 0.5f),
        targetValue = Color.White,
        animationSpec = infiniteRepeatable(
            animation = tween(1000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        )
    )

    Scaffold { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .clickable { viewModel.refreshQuote() }
                .padding(padding)
        ) {
            AnimatedCentralGradientBackground()

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                // Текст цитаты и автора
                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.Center
                ) {
                    val quote = state.currentQuote.substringBeforeLast("—").replace(".", "")
                    val author = state.currentQuote.substringAfterLast("—")

                    Text(
                        text = "\"$quote\"",
                        color = Color.White.copy(alpha = 0.9f),
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.headlineMedium.copy(
                            shadow = Shadow(
                                color = Color.Black.copy(alpha = 0.5f),
                                offset = Offset(2f, 2f),
                                blurRadius = 8f
                            )
                        ),
                        fontStyle = FontStyle.Italic
                    )

                    Text(
                        text = author,
                        modifier = Modifier
                            .padding(top = 32.dp, end = 32.dp)
                            .align(Alignment.End),
                        color = Color.White.copy(alpha = 0.9f),
                        style = MaterialTheme.typography.headlineSmall.copy(
                            shadow = Shadow(
                                color = Color.Black.copy(alpha = 0.5f),
                                offset = Offset(2f, 2f),
                                blurRadius = 8f
                            )
                        )
                    )
                }

                Button(
                    onClick = { /* Действие для кнопки */ },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                        .border(
                            width = 2.dp,
                            color = borderColor,
                            shape = RoundedCornerShape(25.dp)
                        ),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Transparent,
                        contentColor = Color.White
                    ),
                    shape = RoundedCornerShape(25.dp)
                ) {
                    Text(
                        text = "Мои цитаты",
                        style = MaterialTheme.typography.titleMedium
                    )
                }
            }
        }
    }
}
