package com.example.stageconnect.presentation.components

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

object ErrorMessage {
    @Composable
    fun Show(message: String) {
        val context = LocalContext.current
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }
}
