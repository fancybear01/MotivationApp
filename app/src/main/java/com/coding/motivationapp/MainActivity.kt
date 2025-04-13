package com.coding.motivationapp

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.getSystemService
import com.coding.motivationapp.motivation_screen.AlarmReceiver
import com.coding.motivationapp.motivation_screen.MotivationScreen
import com.coding.motivationapp.ui.theme.MotivationAppTheme

class MainActivity : ComponentActivity() {
    private val alarmPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            AlarmReceiver.setNextAlarm(this)
        } else {
            Toast.makeText(
                this,
                "Точные уведомления отключены",
                Toast.LENGTH_LONG
            ).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        createNotificationChannel()
        if (!isAlarmSet()) AlarmReceiver.setNextAlarm(this)
        setContent {
            MotivationAppTheme {
                MotivationScreen(context = this)
            }
        }
    }

    private fun createNotificationChannel() {
        val channel = NotificationChannel(
            "motivation_channel",
            "Motivation Quotes",
            NotificationManager.IMPORTANCE_DEFAULT
        )
        getSystemService<NotificationManager>()?.createNotificationChannel(channel)
    }

    private fun isAlarmSet(): Boolean {
        return PendingIntent.getBroadcast(
            this,
            0,
            Intent(this, AlarmReceiver::class.java),
            PendingIntent.FLAG_NO_CREATE or PendingIntent.FLAG_IMMUTABLE
        ) != null
    }
}



