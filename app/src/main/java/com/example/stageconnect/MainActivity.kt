package com.example.stageconnect

import android.Manifest.permission.POST_NOTIFICATIONS
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.stageconnect.data.remote.repository.StorageRepository
import com.example.stageconnect.domain.CONSTANT
import com.example.stageconnect.presentation.components.ConnectionBanner
import com.example.stageconnect.presentation.components.rememberConnectivityState
import com.example.stageconnect.presentation.navigation.AppNavHost
import com.example.stageconnect.presentation.viewmodels.MainViewModel
import com.example.stageconnect.ui.theme.StageConnectTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private lateinit var mainViewModel: MainViewModel
    private lateinit var context: Context
    @Inject
    lateinit var storageRepository: StorageRepository
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            mainViewModel = hiltViewModel()
            context = LocalContext.current
            mainViewModel.save(label = CONSTANT.JWT_TOKEN, data = "")
            val isConnected by rememberConnectivityState()
            var showGreenBanner by remember { mutableStateOf(false) }
            var wasDisconnected by remember { mutableStateOf(false) }

            LaunchedEffect(isConnected) {
                if (!isConnected) {
                    wasDisconnected = true
                    showGreenBanner = false
                } else if (wasDisconnected) {
                    showGreenBanner = true
                    delay(2000)
                    showGreenBanner = false
                    wasDisconnected = false
                }
            }

            StageConnectTheme {
                Box(modifier = Modifier.fillMaxSize()){
                    AppNavHost(context = this@MainActivity, storageRepository = storageRepository)
                    ConnectionBanner(
                        isConnected = isConnected,
                        wasDisconnected = wasDisconnected,
                        showGreenBanner = showGreenBanner
                    )
                }
            }
            requestNotificationPermission()
        }
    }

    private fun requestNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(
                    this,
                    POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(POST_NOTIFICATIONS),
                    NOTIFICATION_PERMISSION_CODE
                )
            }

        }
    }

    companion object {
        private const val NOTIFICATION_PERMISSION_CODE = 1001
    }

}
