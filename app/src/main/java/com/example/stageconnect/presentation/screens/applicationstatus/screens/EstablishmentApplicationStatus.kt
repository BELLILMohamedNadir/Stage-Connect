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
import com.example.stageconnect.data.dtos.OfferDto
import com.example.stageconnect.data.dtos.RecruiterDto
import com.example.stageconnect.data.dtos.RoomDto
import com.example.stageconnect.data.dtos.UserDto
import com.example.stageconnect.domain.FileUtils
import com.example.stageconnect.domain.model.enums.STATUS
import com.example.stageconnect.presentation.components.AppButton
import com.example.stageconnect.presentation.components.CustomMessage
import com.example.stageconnect.presentation.components.NotFound
import com.example.stageconnect.presentation.components.ObserveResult
import com.example.stageconnect.presentation.components.ProfileImage
import com.example.stageconnect.presentation.screens.applications.viewmodels.ApplicationViewModel
import com.example.stageconnect.presentation.screens.applicationstatus.components.CustomApplicationDialog
import com.example.stageconnect.presentation.screens.applicationstatus.components.CustomCard
import com.example.stageconnect.presentation.screens.applicationstatus.components.CustomUserInformationUpdatedDialog
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
fun EstablishmentApplicationStatus(
    modifier: Modifier = Modifier,
    applicationViewModel: ApplicationViewModel,
    profileViewModel: ProfileViewModel,
    onMessageNavigation: (RoomDto) -> Unit,
    onOfferDetailsClick: (OfferDto) -> Unit,
    onNavigate: () -> Unit,
    onCompanyDetailsClick: () -> Unit,
    onNext: () -> Unit
) {
    val application = applicationViewModel.application.value
    val offer = application?.offerDto
    val user = profileViewModel.user.value
    var fileUri by remember { mutableStateOf<Uri?>(null) }
    var conventionUri by remember { mutableStateOf<Uri?>(null) }
    val isAcceptLoading = remember { mutableStateOf(false) }
    val isRejectLoading = remember { mutableStateOf(false) }
    var showDialog by remember { mutableStateOf(false) }
    var showDialogUserInformationUpdated by remember { mutableStateOf(false) }
    val context = LocalContext.current
    val downloadFileResult by profileViewModel.downloadFileResult.observeAsState()
    val updateApplicationResult by applicationViewModel.updateApplicationResult.observeAsState()
    var currentDownloadType by remember { mutableStateOf("resume") }
    var downloadConvention by remember { mutableStateOf(false) }
    var downloadResume by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        if ((application?.conventionUrl?:"").isNotBlank()){
            application?.conventionUrl?.takeIf { it.isNotBlank() }?.let {
                downloadConvention = true
                profileViewModel.downloadFile(it)
            }
        }else{
            application?.resumeUrl?.takeIf { it.isNotBlank() }?.let {
                downloadResume = true
                profileViewModel.downloadFile(it)
            }
        }
    }

    ObserveResult(
        result = downloadFileResult,
        onLoading = {
            // Show loading indicator if needed
        },
        onSuccess = { response ->
            GlobalScope.launch(Dispatchers.IO) {
                try {
                    response.body()?.byteStream()?.use { inputStream ->
                        val fileName = if (currentDownloadType == "resume") "cv" else if (application?.refused!!) "lettre-refus" else "convention-stage"
                        val uri = FileUtils.savePdfToLocal(
                            context = context,
                            fileName = fileName,
                            inputStream = inputStream
                        )

                        withContext(Dispatchers.Main) {
                            if (currentDownloadType == "resume") {
                                downloadResume = false
                                fileUri = uri
                                if (!application?.conventionUrl.isNullOrBlank()) {
                                    downloadConvention = true
                                    currentDownloadType = "convention"
                                    profileViewModel.downloadFile(application!!.conventionUrl!!)
                                }
                            } else {
                                downloadConvention = false
                                conventionUri = uri
                            }
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

    ObserveResult(
        result = updateApplicationResult,
        onLoading = {
            // Show loading indicator if needed
        },
        onSuccess = { response ->
            // Launch in IO dispatcher for file operations
            isAcceptLoading.value = false
            isRejectLoading.value = false
            showDialog = false
            applicationViewModel.clearData()
            onNext()
        },
        onError = {
            CustomMessage.Show(stringResource(R.string.error_occurred_when_uploading_resume))
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
                                        when(application.establishmentStatus){
                                            STATUS.SENT -> SoftBlue
                                            STATUS.ACCEPTED -> GreenBackground
                                            STATUS.REJECTED -> RedBackground
                                        }),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = application.establishmentStatus.name,
                                    fontWeight = FontWeight.W500,
                                    fontSize = 11.sp,
                                    color =
                                        when(application.establishmentStatus){
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
                            text = application.studentName,
                            textAlign = TextAlign.Center,
                            color = Blue,
                            fontSize = 15.sp,
                            fontWeight = FontWeight.W500
                        )

                        IconButton(
                            onClick = {
                                onMessageNavigation(application.roomDto!!)
                            }
                        ) {
                            Icon(painter = painterResource(R.drawable.ic_message_bottom_navigation), contentDescription = "message Icon", tint = Color.Unspecified)
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))


                    CustomCard(label = stringResource(R.string.offer_details)) {
                        onOfferDetailsClick(application.offerDto!!)
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    CustomCard(label = stringResource(R.string.company_details)) {
                        profileViewModel.setRecruiterDto(RecruiterDto(
                            organizationName = application.companyName,
                            address = application.companyAddress,
                            summary = application.companySummary
                        ))
                        profileViewModel.setShowRecruiterData(true)
                        onCompanyDetailsClick()
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    //cv
                    CustomFileCard(
                        uri = fileUri, context = context,
                        isApplication = true,
                        isLoading = downloadResume,
                        onDeleteClick = { fileUri  = null }) {
                        FileUtils.openPdf(context = context, uri = it)
                    }

                }
            }
            //Accept and Reject Buttons
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = if (application.status != STATUS.SENT) 0.dp else 8.dp, start = if (application.status != STATUS.SENT) 0.dp else 8.dp, top = 8.dp, bottom =  4.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (application.status != STATUS.SENT){
                    Column(
                        modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        CustomFileCard(
                            uri = conventionUri, context = context,
                            isApplication = true,
                            isLoading = downloadConvention,
                            onDeleteClick = { conventionUri  = null }) {
                            FileUtils.openPdf(context = context, uri = it)
                        }
                    }
                }else{
                    if (!isAcceptLoading.value){
                        AppButton(
                            modifier = Modifier.weight(1f),
                            text = stringResource(R.string.reject),
                            contentColor = RedFont,
                            containerColor = RedBackground,
                            isLoading = isRejectLoading
                        ) {
                            if(userInformationUpdate(user = user)){
                                showDialog = true
                            }else{
                                showDialogUserInformationUpdated = true
                            }
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
                            if (userInformationUpdate(user = user)){
                                isAcceptLoading.value = true
                                application.establishmentStatus = STATUS.ACCEPTED
                                applicationViewModel.updateApplication(
                                    application.id!!,
                                    application
                                )
                            }else{
                                showDialogUserInformationUpdated = true
                            }
                        }
                    }
                }
            }
        }
        if (showDialog){
            CustomApplicationDialog(
                label = stringResource(R.string.add_reason),
                application = application,
                isLoading = isRejectLoading,
                onDismiss = {showDialog = false}
            ) { refusedReason ->
                isRejectLoading.value = true
                application.establishmentStatus = STATUS.REJECTED
                application.refusedReason = refusedReason
                applicationViewModel.updateApplication(
                    application.id!!,
                    application
                )
            }
        }
        if (showDialogUserInformationUpdated){
            CustomUserInformationUpdatedDialog(
                label = stringResource(R.string.you_have_to_update_your_information),
                onDismiss = {showDialogUserInformationUpdated = false},
            ) {
                onNavigate()
                showDialogUserInformationUpdated = false
            }
        }
    } else {
        NotFound()
    }
}

fun userInformationUpdate(user: UserDto?): Boolean = (user?.organizationName ?: "").isNotBlank() && (user?.summary ?: "").isNotBlank()
        && (user?.address ?: "").isNotBlank()