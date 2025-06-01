package com.example.stageconnect.presentation.screens.home.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.stageconnect.R
import com.example.stageconnect.data.dtos.OfferDto
import com.example.stageconnect.presentation.components.CustomOfferCard
import com.example.stageconnect.presentation.components.CustomMessage
import com.example.stageconnect.presentation.components.NoDataFound
import com.example.stageconnect.presentation.components.NotFound
import com.example.stageconnect.presentation.components.ObserveResult
import com.example.stageconnect.presentation.components.ProfileImage
import com.example.stageconnect.presentation.screens.home.components.CustomSearchBar
import com.example.stageconnect.presentation.screens.offer.viewmodel.JobDetailsViewModel
import com.example.stageconnect.presentation.screens.profile.viewmodels.ProfileViewModel
import com.example.stageconnect.presentation.viewmodels.OfferViewModel
import com.example.stageconnect.ui.theme.Blue
import com.example.stageconnect.ui.theme.GrayFont
import com.example.stageconnect.ui.theme.LibreBaskerVilleBold

@Composable
fun RecruiterHomeScreen(modifier: Modifier = Modifier,
                        jobDetailsViewModel: JobDetailsViewModel,
                        profileViewModel: ProfileViewModel,
                        offerViewModel: OfferViewModel = hiltViewModel(),
                        onFilterClick: () -> Unit,
                        onOfferCardClick: () -> Unit,
                        onSeeAll: () -> Unit,
                        onSearchClick: () -> Unit) {

    val isLoading = remember { mutableStateOf(false) }
    val offers = offerViewModel.offers.observeAsState(emptyList<OfferDto>())

    val getAllRecruiterOfferResult by offerViewModel.getAllRecruiterOfferResult.observeAsState()

    LaunchedEffect(Unit) {
        offerViewModel.getAllRecruiterOffer()
    }

    ObserveResult(
        result = getAllRecruiterOfferResult,
        onLoading = {isLoading.value = true},
        onSuccess = {
            isLoading.value = false
        },
        onError = {
            isLoading.value = false
        }
    )


    if (!isLoading.value){
        LazyColumn(
            modifier = modifier
                .fillMaxSize()
                .padding(bottom = 4.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(bottom = 10.dp)
        ) {

            item {
                // User Greeting and Search
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 24.dp, start = 24.dp, end = 24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        ProfileImage(profileViewModel.user.value?.photo, 60.dp)
                        Spacer(modifier = Modifier.width(12.dp))
                        Column {
                            Text(
                                text = "Good Morning!",
                                fontFamily = LibreBaskerVilleBold,
                                fontWeight = FontWeight.W500,
                                fontSize = 15.sp,
                                color = GrayFont,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                            Text(
                                text = "${profileViewModel.user.value?.name ?: ""} ${profileViewModel.user.value?.firstName ?: ""}",
                                fontFamily = LibreBaskerVilleBold,
                                fontWeight = FontWeight.W600,
                                fontSize = 15.sp,
                                color = Color.Black,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                        }
                    }
                    if (offers.value.isNotEmpty()){
                        Spacer(modifier = Modifier.height(20.dp))
                        CustomSearchBar(
                            onFilterClick = {onFilterClick()}
                        ) {
                            onSearchClick()
                        }
                        Spacer(modifier = Modifier.height(20.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = stringResource(R.string.offers),
                                fontFamily = LibreBaskerVilleBold,
                                fontWeight = FontWeight.W600,
                                fontSize = 16.sp,
                                color = Color.Black
                            )
                            Text(
                                text = stringResource(R.string.see_all),
                                modifier = Modifier.clickable {
                                    onSeeAll()
                                },
                                fontFamily = LibreBaskerVilleBold,
                                fontWeight = FontWeight.W600,
                                fontSize = 15.sp,
                                color = Blue
                            )
                        }
                        Column(
                            modifier = Modifier.fillMaxWidth().padding(top = 12.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            offers.value.forEach { item ->
                                CustomOfferCard(offerDto = item, showSaveIcon = false){ offer ->
                                    jobDetailsViewModel.setOffer(offer)
                                    onOfferCardClick()
                                }
                            }

                        }
                    }else{
                        NotFound(showMessage = false)
                    }
                }

            }
        }
    }else{
        Column(
            modifier = modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            CircularProgressIndicator()
        }
    }

}