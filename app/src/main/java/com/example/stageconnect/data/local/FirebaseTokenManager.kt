package com.example.stageconnect.data.local

import android.util.Log
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.coroutines.tasks.await

object FirebaseTokenManager {
    fun saveToken(token: String) {
        // Optionally cache locally, send to backend via Repository
        Log.d("FCM", "Token saved: $token")
    }

    suspend fun getCurrentToken(): String? {
        return FirebaseMessaging.getInstance().token.await()
    }
}
