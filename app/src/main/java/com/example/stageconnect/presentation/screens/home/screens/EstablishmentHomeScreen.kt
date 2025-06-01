package com.example.stageconnect.presentation.screens.home.screens

import androidx.compose.foundation.background
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
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.stageconnect.R
import com.example.stageconnect.presentation.components.CustomMessage
import com.example.stageconnect.presentation.components.NoDataFound
import com.example.stageconnect.presentation.components.NotFound
import com.example.stageconnect.presentation.components.ObserveResult
import com.example.stageconnect.presentation.components.ProfileImage
import com.example.stageconnect.presentation.screens.applications.components.CustomEstablishmentApplicationCard
import com.example.stageconnect.presentation.screens.applications.viewmodels.ApplicationViewModel
import com.example.stageconnect.presentation.screens.profile.viewmodels.ProfileViewModel
import com.example.stageconnect.presentation.screens.search.components.CustomSearchBar
import com.example.stageconnect.ui.theme.BackgroundGray
import com.example.stageconnect.ui.theme.Blue
import com.example.stageconnect.ui.theme.GrayFont
import com.example.stageconnect.ui.theme.LibreBaskerVilleBold

@Composable
fun EstablishmentHomeScreen(modifier: Modifier = Modifier,
                            applicationViewModel: ApplicationViewModel,
                            profileViewModel: ProfileViewModel,
                            onFilterClick: () -> Unit,
                            onNavigate: () -> Unit
) {

    val filteredItems by applicationViewModel.filteredApplications.observeAsState(initial = emptyList())
    val applications by applicationViewModel.applications.observeAsState(initial = emptyList())
    var searchedText by remember { mutableStateOf("") }
    val isLoading = remember { mutableStateOf(false) }

    val getEstablishmentApplicationResult by applicationViewModel.getEstablishmentApplicationResult.observeAsState()

    LaunchedEffect(Unit) {
        applicationViewModel.getEstablishmentApplication()
    }

    ObserveResult(
        result = getEstablishmentApplicationResult,
        onLoading = {isLoading.value = true},
        onSuccess = {
            isLoading.value = false
        },
        onError = {
            isLoading.value = false
        }
    )

    if (!isLoading.value){

            // User Greeting and Search
            Column(
                modifier = modifier
                    .fillMaxSize()
                    .padding(bottom = 4.dp)
            ) {
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

                    Spacer(modifier = Modifier.height(20.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(fraction = 0.9f),
                        verticalAlignment = Alignment.CenterVertically) {
                        Column {
                            Spacer(modifier = Modifier.height(5.dp))
                            CustomSearchBar (trailingIcon = -1, onValueChange = {
                                searchedText = it
                                applicationViewModel.applyFilter(searchedText)
                            }){
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(20.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Start
                    ) {
                        Spacer(modifier = Modifier.height(10.dp))
                        if (searchedText.isNotEmpty()){
                            Text(text = "${filteredItems.size} ${stringResource(R.string.found)}",
                                fontFamily = LibreBaskerVilleBold,
                                fontWeight = FontWeight.W600,
                                fontSize = 16.sp)
                        }else{
                            if (applications.isNotEmpty()){
                                Text(
                                    text = stringResource(R.string.applications),
                                    fontFamily = LibreBaskerVilleBold,
                                    fontWeight = FontWeight.W600,
                                    fontSize = 16.sp,
                                    color = Color.Black
                                )
                            }
                        }
                        Spacer(modifier = Modifier.height(16.dp))
                    }
                }
                if (applications.isEmpty()){
                    NotFound(showMessage = false)
                }else{
                    if (filteredItems.isNotEmpty()){
                        Spacer(modifier = Modifier.height(8.dp))
                        LazyColumn(
                            verticalArrangement = Arrangement.spacedBy(16.dp),
                            contentPadding = PaddingValues(bottom = 10.dp)
                        ) {

                            item {
                                Column(
                                    modifier = Modifier.fillMaxWidth().padding(top = 12.dp),
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    verticalArrangement = Arrangement.spacedBy(16.dp)
                                ) {
                                    filteredItems.forEach { item ->
                                        CustomEstablishmentApplicationCard(application = item, circleShape = true){ application ->
                                            applicationViewModel.setApplication(application)
                                            onNavigate()
                                        }
                                        Spacer(modifier = Modifier.height(10.dp))
                                        Spacer(modifier = Modifier.height(2.dp).fillMaxWidth(fraction = 0.8f).background(color = BackgroundGray))
                                    }

                                }
                            }
                        }
                    }else{
                        NoDataFound()
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