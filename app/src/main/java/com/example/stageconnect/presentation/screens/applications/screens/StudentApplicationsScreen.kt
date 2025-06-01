package com.example.stageconnect.presentation.screens.applications.screens

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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.stageconnect.R
import com.example.stageconnect.presentation.components.CustomMessage
import com.example.stageconnect.presentation.components.NoDataFound
import com.example.stageconnect.presentation.components.NotFound
import com.example.stageconnect.presentation.components.ObserveResult
import com.example.stageconnect.presentation.screens.applications.components.CustomStudentApplicationCard
import com.example.stageconnect.presentation.screens.applications.viewmodels.ApplicationViewModel
import com.example.stageconnect.presentation.screens.search.components.CustomSearchBar
import com.example.stageconnect.ui.theme.BackgroundGray
import com.example.stageconnect.ui.theme.LibreBaskerVilleBold

@Composable
fun StudentApplicationsScreen(modifier: Modifier = Modifier,
                              applicationViewModel: ApplicationViewModel,
                              onFilterClick: () -> Unit,
                              onNavigate: () -> Unit
) {

    val filteredItems by applicationViewModel.filteredApplications.observeAsState(initial = emptyList())
    val applications by applicationViewModel.applications.observeAsState(initial = emptyList())
    var searchedText by remember { mutableStateOf("") }
    val isLoading = remember { mutableStateOf(false) }

    val getStudentApplicationResult by applicationViewModel.getStudentApplicationResult.observeAsState()

    LaunchedEffect(Unit) {
        applicationViewModel.getStudentApplication()
    }

    ObserveResult(
        result = getStudentApplicationResult,
        onLoading = {isLoading.value = true},
        onSuccess = {
            isLoading.value = false
        },
        onError = {
            isLoading.value = false
        }
    )

    if (!isLoading.value){
        Column(modifier = modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally) {
            //Header
            Row(
                modifier = Modifier.fillMaxWidth(fraction = 0.8f),
                verticalAlignment = Alignment.CenterVertically) {
                Column {
                    Spacer(modifier = Modifier.height(5.dp))
                    CustomSearchBar (onFilterClick = {onFilterClick() }, trailingIcon = -1, onValueChange = {
                        searchedText = it
                        applicationViewModel.applyFilter(searchedText)
                    }){
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            //Body
            if (applications.isEmpty()){
                NotFound(showMessage = false)
            }else{
                if (filteredItems.isNotEmpty()){
                    // founded elements
                    if (searchedText.isNotEmpty()){
                        Column (
                            modifier = Modifier.fillMaxWidth().padding(start = 50.dp),
                            horizontalAlignment = Alignment.Start,
                        ){
                            if (filteredItems.isNotEmpty()){
                                Spacer(modifier = Modifier.height(10.dp))
                                Text(text = "${filteredItems.size} ${stringResource(R.string.found)}",
                                    fontFamily = LibreBaskerVilleBold,
                                    fontWeight = FontWeight.W600,
                                    fontSize = 16.sp)
                                Spacer(modifier = Modifier.height(16.dp))
                            }
                        }
                    }
                    //application list
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {

                        LazyColumn(
                            verticalArrangement = Arrangement.spacedBy(24.dp),
                            contentPadding = PaddingValues(end = 10.dp, start = 10.dp, top = 24.dp, bottom = 8.dp)
                        ){
                            items(filteredItems.size){ index ->
                                CustomStudentApplicationCard(application = filteredItems[index]){ application ->
                                    applicationViewModel.setApplication(application)
                                    onNavigate()
                                }
                                Spacer(modifier = Modifier.height(10.dp))
                                Spacer(modifier = Modifier.height(2.dp).fillMaxWidth(fraction = 0.8f).background(color = BackgroundGray))
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