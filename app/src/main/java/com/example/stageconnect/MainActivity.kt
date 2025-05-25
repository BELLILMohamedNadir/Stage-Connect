package com.example.stageconnect

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.stageconnect.data.remote.repository.StorageRepository
import com.example.stageconnect.domain.CONSTANT
import com.example.stageconnect.presentation.navigation.AppNavHost
import com.example.stageconnect.presentation.viewmodels.MainViewModel
import com.example.stageconnect.ui.theme.StageConnectTheme
import dagger.hilt.android.AndroidEntryPoint
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
            StageConnectTheme {
                Box(modifier = Modifier.fillMaxSize()){
                    AppNavHost(context = this@MainActivity, storageRepository = storageRepository)
                }
            }
        }
    }

}
