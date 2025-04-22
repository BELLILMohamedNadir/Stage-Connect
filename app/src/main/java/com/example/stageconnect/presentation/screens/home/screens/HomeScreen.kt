package com.example.stageconnect.presentation.screens.home.screens

import androidx.compose.foundation.Image
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.stageconnect.R
import com.example.stageconnect.data.dummy.DummyDataProvider
import com.example.stageconnect.presentation.components.CustomOfferCard
import com.example.stageconnect.presentation.components.CustomCardOption
import com.example.stageconnect.presentation.screens.home.components.CustomSearchBar
import com.example.stageconnect.presentation.screens.jobdetails.components.JobDetailsViewModel
import com.example.stageconnect.ui.theme.Blue
import com.example.stageconnect.ui.theme.GrayFont
import com.example.stageconnect.ui.theme.LibreBaskerVilleBold
import kotlinx.coroutines.delay

@Composable
fun HomeScreen(modifier: Modifier = Modifier,
               jobDetailsViewModel: JobDetailsViewModel,
               onFilterClick: () -> Unit,
               onOfferCardClick: () -> Unit,
               onSearchClick: () -> Unit) {
    //dummy data
    val offers = DummyDataProvider.offers
    val jobs = DummyDataProvider.jobs

    var selectedOption by remember { mutableIntStateOf(0) }
    var ready by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        delay(500)
        ready = true
    }

    if (ready){
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
                    horizontalAlignment = Alignment.Start
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Image(
                            painter = painterResource(R.drawable.img_user),
                            contentDescription = "user image",
                            modifier = Modifier
                                .size(60.dp)
                                .clip(CircleShape),
                            contentScale = ContentScale.Crop
                        )
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
                                text = "BELLIL Mohamed",
                                fontFamily = LibreBaskerVilleBold,
                                fontWeight = FontWeight.W600,
                                fontSize = 15.sp,
                                color = Color.Black,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                        }
                    }
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
                            text = stringResource(R.string.recommendation),
                            fontFamily = LibreBaskerVilleBold,
                            fontWeight = FontWeight.W600,
                            fontSize = 16.sp,
                            color = Color.Black
                        )
                        Text(
                            text = stringResource(R.string.see_all),
                            fontFamily = LibreBaskerVilleBold,
                            fontWeight = FontWeight.W600,
                            fontSize = 15.sp,
                            color = Blue
                        )
                    }
                }
            }

            item {
                // Recommended Offers (Horizontal Scroll)
                LazyRow(
                    modifier = Modifier.fillMaxWidth().padding(start = 24.dp),
                    contentPadding = PaddingValues(end = 10.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(offers.size) { index ->
                        CustomOfferCard(offer = offers[index]){ offer ->
                            jobDetailsViewModel.setOffer(offer)
                            onOfferCardClick()
                        }
                    }
                }
            }

            item {
                // Recent Jobs Title
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 24.dp, start = 24.dp, end = 24.dp),
                    horizontalAlignment = Alignment.Start
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = stringResource(R.string.recent_jobs),
                            fontFamily = LibreBaskerVilleBold,
                            fontWeight = FontWeight.W600,
                            fontSize = 16.sp,
                            color = Color.Black
                        )
                        Text(
                            text = stringResource(R.string.see_all),
                            fontFamily = LibreBaskerVilleBold,
                            fontWeight = FontWeight.W600,
                            fontSize = 15.sp,
                            color = Blue
                        )
                    }
                }
            }

            item {

                LazyRow(
                    modifier = Modifier.fillMaxWidth().padding(start = 24.dp),
                    contentPadding = PaddingValues(end = 10.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(jobs.size) { index ->
                        CustomCardOption(
                            index = index,
                            option = jobs[index],
                            isSelected = selectedOption == index
                        ) {
                            selectedOption = index
                        }
                    }
                }
            }

            // Vertical Offer List
            items(offers.size) { index ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 48.dp),
                ) {
                    CustomOfferCard(offer = offers[index]){ offer ->
                        jobDetailsViewModel.setOffer(offer)
                        onOfferCardClick()
                    }
                }
            }
        }
    }else{
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    }

}