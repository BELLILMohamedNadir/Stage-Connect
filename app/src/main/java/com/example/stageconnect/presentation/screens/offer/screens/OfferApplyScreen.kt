package com.example.stageconnect.presentation.screens.offer.screens

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.stageconnect.R
import com.example.stageconnect.data.dtos.ApplicationDto
import com.example.stageconnect.data.dtos.OfferDto
import com.example.stageconnect.domain.FileUtils
import com.example.stageconnect.presentation.components.AppButton
import com.example.stageconnect.presentation.components.CustomOfferCard
import com.example.stageconnect.presentation.components.CustomTextArea
import com.example.stageconnect.presentation.components.CustomMessage
import com.example.stageconnect.presentation.components.NotFound
import com.example.stageconnect.presentation.components.ObserveResult
import com.example.stageconnect.presentation.screens.applications.viewmodels.ApplicationViewModel
import com.example.stageconnect.presentation.screens.profile.components.CustomFileCard
import com.example.stageconnect.presentation.screens.profile.viewmodels.ProfileViewModel
import com.example.stageconnect.ui.theme.BackgroundGray_
import com.example.stageconnect.ui.theme.Blue

@Composable
fun OfferApplyScreen(modifier: Modifier = Modifier,
                     offer: OfferDto?,
                     viewModel: ProfileViewModel = hiltViewModel(),
                     applicationViewModel: ApplicationViewModel = hiltViewModel(),
                     onNext: () -> Unit
) {
    var fileUri by remember { mutableStateOf<Uri?>(null) }
    val context = LocalContext.current

    val isLoading = remember { mutableStateOf(false) }
    val coverLetter = remember { mutableStateOf("") }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri ->
        uri?.let {
            fileUri = it
            viewModel.onFileSelected(it) }
    }

    val createApplicationResult by applicationViewModel.createApplication.observeAsState()

    ObserveResult(
        result = createApplicationResult,
        onLoading = {isLoading.value = true},
        onSuccess = {
            isLoading.value = false
            onNext()
        },
        onError = {
            isLoading.value = false
            CustomMessage.Show(stringResource(R.string.error_occurred))
        }
    )

    if (offer != null){
        Column(
            modifier = modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Spacer(modifier = Modifier.height(1.dp).fillMaxWidth(0.9f).background(BackgroundGray_))
            Spacer(modifier = Modifier.height(5.dp))
            LazyColumn (
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                contentPadding = PaddingValues(top = 40.dp, end = 16.dp, start = 16.dp)
            ){
                item {
                    CustomOfferCard(offerDto = offer,
                        showDate = true,
                        showLabel = true,
                        onSavedClick = {
                        })
                    Spacer(modifier = Modifier.height(36.dp))

                    Card(
                        modifier = Modifier
                            .fillMaxWidth(fraction = 0.95f)
                            .height(58.dp)
                            .clip(RoundedCornerShape(24.dp)),
                        elevation = CardDefaults.elevatedCardElevation(4.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth()
                                .background(Color.White)
                                .border(width = 1.dp, color = BackgroundGray_, shape = RoundedCornerShape(24.dp))
                                .padding(12.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween) {
                            Icon(painter = painterResource(R.drawable.ic_pdf),
                                contentDescription = "image",
                                modifier = Modifier.size(20.dp),
                                tint = Blue)
                            Text(text = stringResource(R.string.resume),
                                fontWeight = FontWeight.W700,
                                fontSize = 16.sp,
                                color = Color.Black,
                                overflow = TextOverflow.Ellipsis)
                            IconButton(onClick = {
                                launcher.launch("application/pdf")
                            }) {
                                Icon(painter = painterResource(R.drawable.ic_more),
                                    contentDescription = "Add button",
                                    tint = Color.Unspecified)
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                    //file
                    Spacer(modifier = Modifier.height(8.dp))
                    CustomFileCard(
                        modifier = Modifier.fillMaxWidth(fraction = 0.95f),
                        uri = fileUri, context = context,
                        onDeleteClick = { fileUri  = null }) {
                        FileUtils.openPdf(context = context, uri = it)
                    }
                    Spacer(modifier = Modifier.height(20.dp))
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.Start
                    ) {
                        Text(text = stringResource(R.string.cover_letter), color = Color.Black, fontSize = 18.sp)
                        Spacer(modifier = Modifier.height(10.dp))
                        CustomTextArea(
                            label = stringResource(R.string.cover_letter),
                            ) {
                            coverLetter.value = it
                        }
                    }

                    AppButton(text = stringResource(R.string.apply), isLoading = isLoading) {
                        if (fileUri != null && coverLetter.value.isNotEmpty()){
                            val request = ApplicationDto(
                                offerId = offer.id!!,
                                studentId = -1,
                                coverLetter = coverLetter.value
                            )
                            applicationViewModel.createApplication(request, fileUri!!)
                        }
                    }
                }
            }
        }
    }else{
        NotFound(showMessage = false)
    }
}