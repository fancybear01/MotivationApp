package com.coding.motivationapp.motivation_screen

import androidx.compose.animation.animateColor
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import kotlin.math.cos
import kotlin.math.sin
import kotlin.random.Random

@Composable
fun AnimatedCentralGradientBackground() {
    val infiniteTransition = rememberInfiniteTransition()
    val canvasSize = LocalConfiguration.current.screenWidthDp.dp

    // Цвета для радиального градиента
    val gradientColors = listOf(
        Color(0xFF2D2D2D),
        Color(0xFF1A1A1A),
        Color(0xFF0A0A0A)
    )

    // Состояние частиц
    val particles = remember {
        List(150) {
            ParticleState(
                angle = Random.nextFloat() * 360f,
                speed = Random.nextFloat() * 3f + 1f,
                size = Random.nextFloat() * 4f + 2f,
                maxDistance = Random.nextFloat() * 1000f + 500f
            )
        }
    }

    // Анимация времени
    val time by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            tween(10000, easing = LinearEasing)
        )
    )

    Canvas(modifier = Modifier.fillMaxSize()) {
        // Радиальный градиентный фон
        drawRect(
            brush = Brush.radialGradient(
                colors = gradientColors,
                center = center,
                radius = size.maxDimension / 1.5f
            )
        )

        // Отрисовка частиц
        particles.forEach { particle ->
            val distance = time * particle.maxDistance * particle.speed
            val x = center.x + cos(Math.toRadians(particle.angle.toDouble())).toFloat() * distance
            val y = center.y + sin(Math.toRadians(particle.angle.toDouble())).toFloat() * distance

            // Прозрачность уменьшается с расстоянием
            val alpha = 1f - (distance / particle.maxDistance).coerceIn(0f, 1f)

            drawCircle(
                color = Color.White.copy(alpha = alpha * 0.7f),
                radius = particle.size.dp.toPx(),
                center = Offset(x, y),
                blendMode = BlendMode.Screen
            )

        }

    }
}

// Класс для хранения состояния частиц
private data class ParticleState(
    val angle: Float,     // Угол в градусах
    val speed: Float,     // Скорость (1-4)
    val size: Float,      // Размер (2-6)
    val maxDistance: Float // Максимальная дистанция (500-1500)
)