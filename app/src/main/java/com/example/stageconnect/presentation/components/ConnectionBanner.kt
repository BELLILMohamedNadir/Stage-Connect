package com.example.stageconnect.presentation.components

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkRequest
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex

@Composable
fun ConnectionBanner(
    isConnected: Boolean?,
    wasDisconnected: Boolean,
    showGreenBanner: Boolean
) {
    val backgroundColor = when {
        !isConnected!! -> Color(0xFFD32F2F) // Red
        showGreenBanner -> Color(0xFF388E3C) // Green
        else -> Color.Transparent
    }

    val message = when {
        !isConnected -> "No Internet Connection"
        showGreenBanner -> "Back Online"
        else -> ""
    }

    AnimatedVisibility(
        visible = !isConnected || showGreenBanner,
        enter = fadeIn() + slideInVertically(initialOffsetY = { -40 }),
        exit = fadeOut() + slideOutVertically(targetOffsetY = { -40 }),
        modifier = Modifier
            .fillMaxWidth()
            .height(40.dp)
            .zIndex(1f)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(backgroundColor),
            contentAlignment = Alignment.Center
        ) {
            Text(text = message, color = Color.White, fontSize = 14.sp)
        }
    }
}


@Composable
fun rememberConnectivityState(): State<Boolean> {
    val context = LocalContext.current
    val connectivityManager = remember {
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    }
    val connectionState = remember { mutableStateOf(isConnected(connectivityManager)) }

    DisposableEffect(Unit) {
        val callback = object : ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) {
                connectionState.value = true
            }

            override fun onLost(network: Network) {
                connectionState.value = false
            }
        }

        val request = NetworkRequest.Builder().build()
        connectivityManager.registerNetworkCallback(request, callback)

        onDispose {
            connectivityManager.unregisterNetworkCallback(callback)
        }
    }

    return connectionState
}

private fun isConnected(connectivityManager: ConnectivityManager): Boolean {
    return connectivityManager.activeNetworkInfo?.isConnected == true
}


