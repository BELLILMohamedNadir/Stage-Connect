package com.example.stageconnect.presentation.screens.applicationstatus.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.stageconnect.R
import com.example.stageconnect.presentation.components.AppButton
import com.example.stageconnect.presentation.components.CardOption
import com.example.stageconnect.presentation.components.NotFound
import com.example.stageconnect.presentation.screens.applications.viewmodels.ApplicationViewModel
import com.example.stageconnect.ui.theme.BackgroundGray_
import com.example.stageconnect.ui.theme.BlackFont
import com.example.stageconnect.ui.theme.Blue
import com.example.stageconnect.ui.theme.BlueFont
import com.example.stageconnect.ui.theme.GreenBackground
import com.example.stageconnect.ui.theme.GreenFont
import com.example.stageconnect.ui.theme.LibreBaskerVilleBold
import com.example.stageconnect.ui.theme.RedBackground
import com.example.stageconnect.ui.theme.RedFont
import com.example.stageconnect.ui.theme.SoftBlue
import kotlinx.coroutines.delay

@Composable
fun ApplicationStatus(modifier: Modifier = Modifier,
                      applicationViewModel : ApplicationViewModel) {
    val offer = applicationViewModel.application.value?.offer
    val status = applicationViewModel.application.value?.status
    var ready by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        delay(500)
        ready = true
    }

    if (ready){
        if (offer != null && status != null){
            Column(modifier = modifier.fillMaxSize().padding(horizontal = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceBetween) {
                Box(
                    modifier = Modifier.height(320.dp).fillMaxWidth(fraction = 0.9f)
                        .clip(shape = RoundedCornerShape(30.dp))
                        .border(width = 1.dp, color = BackgroundGray_, shape = RoundedCornerShape(30.dp))
                ){
                    Column(
                        modifier = Modifier.fillMaxSize().padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        //logo
                        Box(modifier = Modifier
                            .size(70.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .border(width = 1.dp, color = BackgroundGray_, shape =  RoundedCornerShape(10.dp))
                            , contentAlignment = Alignment.Center){
                            Image(painter = painterResource(offer.logo), contentDescription = "Company Image")
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
                            text = offer.company,
                            textAlign = TextAlign.Center,
                            color = Blue,
                            fontSize = 15.sp,
                            fontWeight = FontWeight.W500
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        Spacer(modifier = Modifier.height(1.dp).fillMaxWidth(fraction = 0.95f).background(color = BackgroundGray_))
                        Spacer(modifier = Modifier.height(20.dp))
                        //Place , salary and options
                        Column(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(text = offer.location,
                                fontFamily = LibreBaskerVilleBold,
                                fontWeight = FontWeight.W500,
                                fontSize = 14.sp,
                                color = BlackFont,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis)
                            Spacer(modifier = Modifier.height(6.dp))
                            Text(text = offer.salary,
                                fontFamily = LibreBaskerVilleBold,
                                fontWeight = FontWeight.W500,
                                fontSize = 12.sp,
                                color = Blue,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis)
                            Spacer(modifier = Modifier.height(16.dp))
                            LazyRow(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(8.dp),
                                contentPadding = PaddingValues(start = 24.dp)
                            ) {
                                items(offer.options){ option ->
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
                    Spacer(modifier = Modifier.height(1.dp).fillMaxWidth(fraction = 0.85f).background(color = BackgroundGray_))
                    Spacer(modifier = Modifier.height(30.dp))
                    Text(
                        text = stringResource(R.string.your_application_status),
                        fontSize = 20.sp,
                        fontWeight = FontWeight.W500,
                        color = Color.Black
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    AppButton(text = status.label,
                        fontWeight = FontWeight.W700,
                        containerColor = when(status){
                            com.example.stageconnect.data.model.enum.ApplicationStatus.SENT -> SoftBlue
                            com.example.stageconnect.data.model.enum.ApplicationStatus.ACCEPTED -> GreenBackground
                            com.example.stageconnect.data.model.enum.ApplicationStatus.REJECTED -> RedBackground
                        },
                        contentColor = when(status){
                            com.example.stageconnect.data.model.enum.ApplicationStatus.SENT -> Blue
                            com.example.stageconnect.data.model.enum.ApplicationStatus.ACCEPTED -> GreenFont
                            com.example.stageconnect.data.model.enum.ApplicationStatus.REJECTED -> RedFont
                        },
                        withElevation = false) { }
                }

                AppButton(
                    text = stringResource(R.string.delete_application),
                    fontWeight = FontWeight.W700,
                    withElevation = false) {

                }
            }
        }else{
            NotFound()
        }
    }else{
       Column(
           modifier = Modifier.fillMaxSize(),
           horizontalAlignment = Alignment.CenterHorizontally,
           verticalArrangement = Arrangement.Center
       ) {
           CircularProgressIndicator()
       }
    }
}