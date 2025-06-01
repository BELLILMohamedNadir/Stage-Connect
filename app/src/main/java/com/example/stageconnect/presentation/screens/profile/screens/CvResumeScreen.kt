package com.example.stageconnect.presentation.screens.profile.screens

import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.stageconnect.R
import com.example.stageconnect.domain.FileUtils
import com.example.stageconnect.presentation.components.AppButton
import com.example.stageconnect.presentation.components.CustomMessage
import com.example.stageconnect.presentation.components.NotFound
import com.example.stageconnect.presentation.components.ObserveResult
import com.example.stageconnect.presentation.screens.profile.components.CustomFileCard
import com.example.stageconnect.presentation.screens.profile.viewmodels.ProfileViewModel
import com.example.stageconnect.ui.theme.BackgroundGray
import com.example.stageconnect.ui.theme.BackgroundGray_
import com.example.stageconnect.ui.theme.GrayFont
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun CvResumeScreen(modifier: Modifier = Modifier,
                viewModel: ProfileViewModel,
                   onNext: () -> Unit
) {
    val user = viewModel.user.value
    var fileUri by remember { mutableStateOf<Uri?>(null) }
    val context = LocalContext.current
    val isLoading = rememberSaveable { mutableStateOf(false) }
    var downloadResume by rememberSaveable { mutableStateOf(false) }
    var showErrorMessage by rememberSaveable { mutableStateOf(false) }
    val uploadFileResult by viewModel.uploadFileResult.observeAsState()
    val downloadFileResult by viewModel.downloadFileResult.observeAsState()

    LaunchedEffect(Unit) {
        if (user?.resume != null && user.resume!!.isNotBlank()){
            downloadResume = true
            viewModel.downloadFile(user.resume!!)
        }
    }

    ObserveResult(
        result = uploadFileResult,
        onLoading = {isLoading.value = true},
        onSuccess = {
            isLoading.value = false
            viewModel.clearData()
            onNext()
        },
        onError = {
            isLoading.value = false
            CustomMessage.Show(stringResource(R.string.error_occurred))
        }
    )

    ObserveResult(
        result = downloadFileResult,
        onLoading = {
            // Show loading indicator if needed
        },
        onSuccess = { response ->
            // Launch in IO dispatcher for file operations
            GlobalScope.launch(Dispatchers.IO) {
                try {
                    response.body()?.byteStream()?.use { inputStream ->
                        val uri = FileUtils.savePdfToLocal(
                            context = context,
                            fileName = "cv",
                            inputStream = inputStream
                        )

                        withContext(Dispatchers.Main) {
                            downloadResume = false
                            fileUri = uri
                        }
                    }
                } catch (e: Exception) {
                    Log.e("FileSave", "Error saving file", e)
                }
            }
        },
        onError = {
            CustomMessage.Show(stringResource(R.string.error_occurred_when_uploading_resume))
        }
    )

    if (user != null) {

        val launcher = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.GetContent()
        ) { uri ->
            uri?.let {
                fileUri = it
                viewModel.onFileSelected(it) }
        }

        Column(
            modifier = modifier.fillMaxSize().padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                modifier = Modifier.weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)) {
                //upload cv/resume
                Column (
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 10.dp),
                    horizontalAlignment = Alignment.Start,
                ){
                    Text(text = stringResource(R.string.upload_cv_resume), color = GrayFont, fontSize = 14.sp,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 16.dp))
                    Spacer(modifier = Modifier.height(10.dp))
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp),
                        contentAlignment = Alignment.Center
                    ){
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .clip(shape = RoundedCornerShape(20.dp))
                                .border(width = 1.dp, color = BackgroundGray_, shape = RoundedCornerShape(20.dp))
                                .background(color = BackgroundGray, shape = RoundedCornerShape(20.dp))
                                .clickable {
                                    launcher.launch("application/pdf")
                                },
                            horizontalAlignment = Alignment.CenterHorizontally,
                        ) {
                            Spacer(modifier = Modifier.fillMaxHeight(fraction = 0.35f))
                            Icon(painter = painterResource(R.drawable.ic_file),
                                contentDescription = "file icon",
                                tint = Color.Unspecified)
                            Spacer(modifier = Modifier.height(20.dp))
                            Text(text = stringResource(R.string.browse_file), color = GrayFont)
                        }
                    }

                }
                //file
                Spacer(modifier = Modifier.height(8.dp))
                CustomFileCard(uri = fileUri, context = context,
                    isLoading = downloadResume,
                    onDeleteClick = { fileUri  = null }) {
                    FileUtils.openPdf(context = context, uri = it)
                }
            }
            AppButton(text = stringResource(R.string.save), isLoading = isLoading) {
                if (fileUri != null)
                    viewModel.uploadFile(fileUri)
                else
                    showErrorMessage = true
            }
        }
    } else {
        NotFound(showMessage = false)
    }

    if (showErrorMessage) {
        CustomMessage.Show(stringResource(R.string.add_necessary_data))
        showErrorMessage = false
    }
}