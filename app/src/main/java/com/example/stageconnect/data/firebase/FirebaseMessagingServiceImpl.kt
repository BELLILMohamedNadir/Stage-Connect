package com.example.stageconnect.data.firebase

import android.util.Log
import com.example.stageconnect.data.local.FirebaseTokenManager
import com.example.stageconnect.presentation.notification.NotificationDispatcher
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class FirebaseMessagingServiceImpl : FirebaseMessagingService() {

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        // Ideally send this token to the server via a use case
        FirebaseTokenManager.saveToken(token)
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        Log.d("FCM", "Message received")
        val title = remoteMessage.notification?.title ?: "No title"
        val body = remoteMessage.notification?.body ?: "No message"
        NotificationDispatcher.show(applicationContext, title, body)
    }
}
