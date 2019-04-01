package com.example.pushnotification.api.notification

import android.util.Log
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MyFirebaseMessagingService : FirebaseMessagingService() {

    override fun onNewToken(token: String?) {
        Log.i(TAG, token)

        val instance = FirebaseMessaging.getInstance()
        instance.subscribeToTopic("MAIN")
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        val notification = remoteMessage.notification

        Log.i(TAG, "FCM Notification: $notification")
        Log.i(TAG, "FCM Message ID: ${remoteMessage.messageId}")
        Log.i(TAG, "FCM Data Message: ${remoteMessage.data}")

        notification?.let {
            val title = it.title ?: ""
            val body = it.body ?: ""

            Log.i(TAG, "FCM Notification title: $title")
            Log.i(TAG, "FCM Notification body: $body")

            NotificationCreation.create(this, title, body, false)
        }
    }

    companion object {
        private const val TAG = "FMService"
    }
}
