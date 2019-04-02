package com.example.aula6

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat

object NotificationCreation {
    private const val NOTIFY_ID = 1000
    private val vibration = longArrayOf(300, 400, 500, 400, 300)

    private var notificationManager: NotificationManager? = null

    private const val CHANNEL_ID = "KotlinPush_1"
    private const val CHANNEL_NAME = "Kotlin - Push Channel 1"
    private const val CHANNEL_DESCRIPTION = "Kotlin - Push Channel Description"

    fun create(context: Context, title: String, body: String) {
        if (notificationManager == null) {
            notificationManager = context
                .getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        }

        notificationManager?.let { notificationManager ->
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val channel = notificationManager.getNotificationChannel(CHANNEL_ID)
                if (channel == null) {
                    val newChannel = NotificationChannel(
                        CHANNEL_ID,
                        CHANNEL_NAME,
                        NotificationManager.IMPORTANCE_HIGH
                    )
                    newChannel.description = CHANNEL_DESCRIPTION
                    newChannel.enableVibration(true)
                    newChannel.enableLights(true)
                    newChannel.vibrationPattern = vibration

                    notificationManager.createNotificationChannel(newChannel)
                }
            }

            val intent = Intent(context, MainActivity::class.java)
            val pedingIntent = PendingIntent.getActivity(context, 0, intent, 0)
            val notification = NotificationCompat.Builder(context, CHANNEL_ID)
                .setContentTitle(title)
                .setContentText(body)
                .setSmallIcon(android.R.drawable.ic_dialog_alert)
                .setContentIntent(pedingIntent)
                .setAutoCancel(true)
                .setStyle(NotificationCompat.BigTextStyle().bigText(body))
                .setOnlyAlertOnce(true)
                .build()

            notificationManager.notify(NOTIFY_ID, notification)
        }
    }
}