package com.example.stageconnect.presentation.screens.offer.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.stageconnect.data.dtos.OfferDto
import com.example.stageconnect.presentation.components.CustomOfferCard
import com.example.stageconnect.presentation.components.NotFound
import com.example.stageconnect.presentation.screens.offer.viewmodel.JobDetailsViewModel
import com.example.stageconnect.ui.theme.BackgroundGray_

@Composable
fun JobDetailsScreen(modifier: Modifier = Modifier,
                     jobDetailsViewModel: JobDetailsViewModel,
                     onApply: (OfferDto) -> Unit) {

    val offer = jobDetailsViewModel.offer.value

    if (offer == null){
        NotFound()
    }else{
        LazyColumn (
            modifier = modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            contentPadding = PaddingValues(top = 40.dp, end = 10.dp, start = 10.dp)
        ) {
            item{
                CustomOfferCard(offerDto = offer,
                    showDate = true,
                    showLabel = true,
                    onSavedClick = {
                    })
                Spacer(modifier = Modifier.height(36.dp))
                Spacer(modifier = Modifier.height(1.dp).fillMaxWidth(fraction = 0.9f).background(BackgroundGray_))
                Spacer(modifier = Modifier.height(10.dp))
                JobDescriptionScreen(offer = offer){
                    onApply(offer)
                }
            }
        }
    }
}