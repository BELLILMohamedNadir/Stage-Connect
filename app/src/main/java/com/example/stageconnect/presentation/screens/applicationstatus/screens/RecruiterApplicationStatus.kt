package com.example.stageconnect.presentation.screens.applicationstatus.screens

import android.net.Uri
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.stageconnect.R
import com.example.stageconnect.domain.FileUtils
import com.example.stageconnect.domain.model.enums.STATUS
import com.example.stageconnect.presentation.components.AppButton
import com.example.stageconnect.presentation.components.CustomTextArea
import com.example.stageconnect.presentation.components.ErrorMessage
import com.example.stageconnect.presentation.components.NotFound
import com.example.stageconnect.presentation.components.ObserveResult
import com.example.stageconnect.presentation.components.ProfileImage
import com.example.stageconnect.presentation.screens.applications.viewmodels.ApplicationViewModel
import com.example.stageconnect.presentation.screens.profile.components.CustomFileCard
import com.example.stageconnect.presentation.screens.profile.viewmodels.ProfileViewModel
import com.example.stageconnect.ui.theme.BackgroundGray_
import com.example.stageconnect.ui.theme.Blue
import com.example.stageconnect.ui.theme.GreenBackground
import com.example.stageconnect.ui.theme.GreenFont
import com.example.stageconnect.ui.theme.RedBackground
import com.example.stageconnect.ui.theme.RedFont
import com.example.stageconnect.ui.theme.SoftBlue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun RecruiterApplicationStatus(
    modifier: Modifier = Modifier,
    applicationViewModel: ApplicationViewModel,
    profileViewModel: ProfileViewModel,
    onMessageNavigation: () -> Unit,
    onNext: () -> Unit
) {
    val application = applicationViewModel.application.value
    val offer = application?.offerDto
    var fileUri by remember { mutableStateOf<Uri?>(null) }
    val isAcceptLoading = remember { mutableStateOf(false) }
    val isRejectLoading = remember { mutableStateOf(false) }
    val context = LocalContext.current
    val downloadFileResult by profileViewModel.downloadFileResult.observeAsState()


    LaunchedEffect(Unit) {
        if (application?.resumeUrl != null && application.resumeUrl!!.isNotBlank())
            profileViewModel.downloadFile(application.resumeUrl!!)
    }

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
                            fileUri = uri
                        }
                    }
                } catch (e: Exception) {
                    Log.e("FileSave", "Error saving file", e)
                }
            }
        },
        onError = {
            ErrorMessage.Show(stringResource(R.string.error_occurred_when_uploading_resume))
        }
    )


    if (application != null && offer != null) {
        Column(
            modifier = Modifier.fillMaxSize().padding(horizontal = 24.dp, vertical = 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            LazyColumn (
                modifier = modifier
                    .fillMaxSize()
                    .weight(1f),
            ) {
                item {
                    Column(
                        modifier = Modifier.fillMaxWidth().padding(horizontal = 24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        Spacer(modifier = Modifier.height(2.dp))
                        //Position
                        Text(
                            text = offer.position,
                            textAlign = TextAlign.Center,
                            color = Color.Black,
                            fontSize = 24.sp,
                            fontWeight = FontWeight.W500,
                            modifier = Modifier.align(Alignment.Start)
                        )
                        Spacer(modifier = Modifier.height(2.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            //status
                            Box(
                                modifier = Modifier
                                    .height(27.dp)
                                    .width(140.dp)
                                    .clip(RoundedCornerShape(5.dp))
                                    .background(color =
                                        when(application.recruiterStatus){
                                            STATUS.SENT -> SoftBlue
                                            STATUS.ACCEPTED -> GreenBackground
                                            STATUS.REJECTED -> RedBackground
                                        }),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = application.recruiterStatus.name,
                                    fontWeight = FontWeight.W500,
                                    fontSize = 11.sp,
                                    color =
                                        when(application.recruiterStatus){
                                            STATUS.SENT -> Blue
                                            STATUS.ACCEPTED -> GreenFont
                                            STATUS.REJECTED -> RedFont
                                        }
                                )
                            }
                            Text(
                                text = application.date?:"",
                                textAlign = TextAlign.Center,
                                color = Color.Black,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.W500
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(32.dp))

                    Spacer(
                        modifier = Modifier
                            .height(1.dp)
                            .fillMaxWidth(fraction = 1f)
                            .background(color = BackgroundGray_)
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    //logo, name, message icon
                    Column(modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(4.dp)) {
                        ProfileImage(imageUri = application.logoUrl, imageSize = 80.dp)
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = application.studentName ?: "",
                            textAlign = TextAlign.Center,
                            color = Blue,
                            fontSize = 15.sp,
                            fontWeight = FontWeight.W500
                        )

                        IconButton(
                            onClick = {
                                onMessageNavigation()
                            }
                        ) {
                            Icon(painter = painterResource(R.drawable.ic_message_bottom_navigation), contentDescription = "message Icon", tint = Color.Unspecified)
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    //cv
                    CustomFileCard(
                        uri = fileUri, context = context,
                        isApplication = true,
                        onDeleteClick = { fileUri  = null }) {
                        FileUtils.openPdf(context = context, uri = it)
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    //profile_summary
                    Text(
                        text = stringResource(R.string.profile_summary),
                        textAlign = TextAlign.Start,
                        color = Color.Black,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.W500,
                        modifier = Modifier.align(Alignment.Start)
                    )
                    Text(
                        text = "",
                        textAlign = TextAlign.Start,
                        color = Color.Black,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.W500,
                        modifier = Modifier.align(Alignment.Start).padding(start = 8.dp)
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    //work_experience
                    Text(
                        text = stringResource(R.string.work_experience),
                        textAlign = TextAlign.Start,
                        color = Color.Black,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.W500,
                        modifier = Modifier.align(Alignment.Start)
                    )
                    Text(
                        text = "",
                        textAlign = TextAlign.Start,
                        color = Color.Black,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.W500,
                        modifier = Modifier.align(Alignment.Start).padding(start = 8.dp)
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    //education
                    Text(
                        text = stringResource(R.string.education),
                        textAlign = TextAlign.Start,
                        color = Color.Black,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.W500,
                        modifier = Modifier.align(Alignment.Start)
                    )
                    Text(
                        text = "",
                        textAlign = TextAlign.Start,
                        color = Color.Black,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.W500,
                        modifier = Modifier.align(Alignment.Start).padding(start = 8.dp)
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    //certifications
                    Text(
                        text = stringResource(R.string.certifications),
                        textAlign = TextAlign.Start,
                        color = Color.Black,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.W500,
                        modifier = Modifier.align(Alignment.Start)
                    )
                    Text(
                        text = "",
                        textAlign = TextAlign.Start,
                        color = Color.Black,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.W500,
                        modifier = Modifier.align(Alignment.Start).padding(start = 8.dp)
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    //projects
                    Text(
                        text = stringResource(R.string.projects),
                        textAlign = TextAlign.Start,
                        color = Color.Black,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.W500,
                        modifier = Modifier.align(Alignment.Start)
                    )
                    Text(
                        text = "",
                        textAlign = TextAlign.Start,
                        color = Color.Black,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.W500,
                        modifier = Modifier.align(Alignment.Start).padding(start = 8.dp)
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    //cover letter
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.Start
                    ) {
                        Text(text = stringResource(R.string.cover_letter), color = Color.Black, fontSize = 18.sp)
                        Spacer(modifier = Modifier.height(10.dp))
                        CustomTextArea(
                            enabled = false,
                            label = stringResource(R.string.cover_letter),
                            defaultText = application.coverLetter
                        ) {
                        }
                    }
                }
            }
            //Accept and Reject Buttons
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 8.dp, start = 8.dp, top = 8.dp, bottom = 4.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (!isAcceptLoading.value){
                    AppButton(
                        modifier = Modifier.weight(1f),
                        text = stringResource(R.string.reject),
                        contentColor = RedFont,
                        containerColor = RedBackground,
                        isLoading = isRejectLoading
                    ) {

                    }

                }
                if (!isAcceptLoading.value || !isRejectLoading.value){
                    Spacer(modifier = Modifier.width(12.dp))
                }
                if (!isRejectLoading.value){
                    AppButton(
                        modifier = Modifier.weight(1f),
                        text = stringResource(R.string.accept),
                        contentColor = GreenFont,
                        containerColor = GreenBackground,
                        isLoading = isAcceptLoading
                    ) {

                    }
                }
            }
        }
    } else {
        NotFound()
    }
}