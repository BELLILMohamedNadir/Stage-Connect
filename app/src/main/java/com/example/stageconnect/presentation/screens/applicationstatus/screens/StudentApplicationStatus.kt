package com.example.stageconnect.presentation.screens.applicationstatus.screens

import android.net.Uri
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.stageconnect.R
import com.example.stageconnect.domain.FileUtils
import com.example.stageconnect.domain.model.enums.STATUS
import com.example.stageconnect.presentation.components.AppButton
import com.example.stageconnect.presentation.components.CardOption
import com.example.stageconnect.presentation.components.CustomMessage
import com.example.stageconnect.presentation.components.NotFound
import com.example.stageconnect.presentation.components.ObserveResult
import com.example.stageconnect.presentation.screens.applications.viewmodels.ApplicationViewModel
import com.example.stageconnect.presentation.screens.profile.components.CustomFileCard
import com.example.stageconnect.presentation.screens.profile.viewmodels.ProfileViewModel
import com.example.stageconnect.ui.theme.BackgroundGray_
import com.example.stageconnect.ui.theme.BlackFont
import com.example.stageconnect.ui.theme.Blue
import com.example.stageconnect.ui.theme.GreenBackground
import com.example.stageconnect.ui.theme.GreenFont
import com.example.stageconnect.ui.theme.LibreBaskerVilleBold
import com.example.stageconnect.ui.theme.RedBackground
import com.example.stageconnect.ui.theme.RedFont
import com.example.stageconnect.ui.theme.SoftBlue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun StudentApplicationStatus(
    modifier: Modifier = Modifier,
    applicationViewModel: ApplicationViewModel,
    profileViewModel: ProfileViewModel,
    onNext: () -> Unit
) {
    val application = applicationViewModel.application.value
    val offer = application?.offerDto
    val context = LocalContext.current
    var fileUri by remember { mutableStateOf<Uri?>(null) }

    val isLoading = remember { mutableStateOf(false) }
    var downloadConvention by remember { mutableStateOf(false) }

    val deleteApplicationResult by applicationViewModel.deleteApplicationResult.observeAsState()
    val downloadFileResult by profileViewModel.downloadFileResult.observeAsState()

    LaunchedEffect(Unit) {
        if (application?.conventionUrl != null && application.conventionUrl!!.isNotBlank()){
            downloadConvention = true
            profileViewModel.downloadFile(application.conventionUrl!!)
        }
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
                    val fileName = if (application?.refused!!) "lettre-refus" else "convention-stage"

                    response.body()?.byteStream()?.use { inputStream ->
                        val uri = FileUtils.savePdfToLocal(
                            context = context,
                            fileName = fileName,
                            inputStream = inputStream
                        )

                        withContext(Dispatchers.Main) {
                            fileUri = uri
                            downloadConvention = false
                        }
                    }
                } catch (e: Exception) {
                    Log.e("FileSave", "Error saving file", e)
                }
            }
        },
        onError = {
            CustomMessage.Show(stringResource(R.string.error_occurred_when_uploading_convention))
        }
    )




    ObserveResult(
        result = deleteApplicationResult,
        onLoading = { isLoading.value = true },
        onSuccess = {
            isLoading.value = false
            applicationViewModel.clearData()
            onNext()
        },
        onError = {
            isLoading.value = false
            CustomMessage.Show(stringResource(R.string.error_occurred))
        }
    )


    if (application != null && offer != null) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Box(
                modifier = Modifier
                    .height(320.dp)
                    .fillMaxWidth(fraction = 0.9f)
                    .clip(shape = RoundedCornerShape(30.dp))
                    .border(
                        width = 1.dp,
                        color = BackgroundGray_,
                        shape = RoundedCornerShape(30.dp)
                    )
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    //logo
                    Box(
                        modifier = Modifier
                            .size(70.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .border(
                                width = 1.dp,
                                color = BackgroundGray_,
                                shape = RoundedCornerShape(10.dp)
                            ), contentAlignment = Alignment.Center
                    ) {
                        AsyncImage(
                            model = application.logoUrl,
                            contentDescription = "company logo",
                            placeholder = painterResource(R.drawable.ic_company),
                            error = painterResource(R.drawable.ic_company),
                            contentScale = ContentScale.Crop
                        )
                    }
                    Spacer(modifier = Modifier.height(24.dp))
                    //position
                    Text(
                        text = offer.position,
                        textAlign = TextAlign.Center,
                        color = Color.Black,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.W500
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    //company
                    Text(
                        text = offer.company ?: "",
                        textAlign = TextAlign.Center,
                        color = Blue,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.W500
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Spacer(
                        modifier = Modifier
                            .height(1.dp)
                            .fillMaxWidth(fraction = 0.95f)
                            .background(color = BackgroundGray_)
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                    //Place , salary and options
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = offer.location,
                            fontFamily = LibreBaskerVilleBold,
                            fontWeight = FontWeight.W500,
                            fontSize = 14.sp,
                            color = BlackFont,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = "${offer.salaryStart} - ${offer.salaryEnd} £",
                            fontFamily = LibreBaskerVilleBold,
                            fontWeight = FontWeight.W500,
                            fontSize = 12.sp,
                            color = Blue,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        LazyRow(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            contentPadding = PaddingValues(start = 24.dp)
                        ) {
                            items(offer.options) { option ->
                                CardOption(option = option)
                            }
                        }
                    }
                }
            }

            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(
                    modifier = Modifier
                        .height(1.dp)
                        .fillMaxWidth(fraction = 0.85f)
                        .background(color = BackgroundGray_)
                )
                Spacer(modifier = Modifier.height(30.dp))
                Text(
                    text = stringResource(R.string.your_application_status),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.W500,
                    color = Color.Black
                )
                Spacer(modifier = Modifier.height(10.dp))
                AppButton(
                    text = application.status.name,
                    fontWeight = FontWeight.W700,
                    containerColor = when (application.status) {
                        STATUS.SENT -> SoftBlue
                        STATUS.ACCEPTED -> GreenBackground
                        STATUS.REJECTED -> RedBackground
                    },
                    contentColor = when (application.status) {
                        STATUS.SENT -> Blue
                        STATUS.ACCEPTED -> GreenFont
                        STATUS.REJECTED -> RedFont
                    },
                    withElevation = false
                ) { }
            }

            if (application.status == STATUS.SENT){
                AppButton(
                    text = stringResource(R.string.delete_application),
                    fontWeight = FontWeight.W700,
                    containerColor = RedFont,
                    withElevation = false,
                    isLoading = isLoading
                ) {
                    applicationViewModel.deleteApplication(application.id!!)
                }
            }else{
                Column(
                    modifier = Modifier.fillMaxWidth().padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    CustomFileCard(
                        uri = fileUri, context = context,
                        isApplication = true,
                        isLoading = downloadConvention,
                        onDeleteClick = { fileUri  = null }) {
                        FileUtils.openPdf(context = context, uri = it)
                    }
                }
            }
        }
    } else {
        NotFound()
    }
}