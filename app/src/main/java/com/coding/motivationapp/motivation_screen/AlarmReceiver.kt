package com.coding.motivationapp.motivation_screen

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.provider.Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.Icon
import androidx.core.app.NotificationCompat
import androidx.core.content.getSystemService
import com.coding.motivationapp.R
import kotlin.random.Random

class AlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent?) {
        showNotification(context)
        setNextAlarm(context)
    }

    private fun setNextAlarm(context: Context) {
        val quotes = context.resources.getStringArray(R.array.motivational_quotes)
        val randomQuote = quotes.random()
        val notificationManager = context.getSystemService<NotificationManager>()
        val notification = NotificationCompat.Builder(context, "motivation_channel")
            .setContentTitle("Мотивация дня")
            .setContentText(randomQuote)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setAutoCancel(true)
            .build()

        notificationManager?.notify(Random.nextInt(), notification)
    }

    private fun showNotification(context: Context) {

    }

    companion object {
        fun setNextAlarm(context: Context) {
            val alarmManager = context.getSystemService<AlarmManager>()!!

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                if (!alarmManager.canScheduleExactAlarms()) {
                    val intent = Intent(ACTION_REQUEST_SCHEDULE_EXACT_ALARM)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                    context.startActivity(intent)
                    return
                }
            }

            val intent = Intent(context, AlarmReceiver::class.java)
            val pendingIntent = PendingIntent.getBroadcast(
                context,
                0,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )

            val triggerTime = System.currentTimeMillis() + (1..24).random() * 60 * 60 * 1000

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                alarmManager.setExactAndAllowWhileIdle(
                    AlarmManager.RTC_WAKEUP,
                    triggerTime,
                    pendingIntent
                )
            } else {
                alarmManager.setExact(
                    AlarmManager.RTC_WAKEUP,
                    triggerTime,
                    pendingIntent
                )
            }
        }
    }
}