package com.example.stageconnect.presentation.screens.establishmentstudent.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.stageconnect.R
import com.example.stageconnect.presentation.components.CustomMessage
import com.example.stageconnect.presentation.components.NoDataFound
import com.example.stageconnect.presentation.components.NotFound
import com.example.stageconnect.presentation.components.ObserveResult
import com.example.stageconnect.presentation.screens.establishmentstudent.component.CustomEstablishmentStudentCard
import com.example.stageconnect.presentation.screens.establishmentstudent.viewmodels.EstablishmentViewModel
import com.example.stageconnect.presentation.screens.profile.viewmodels.ProfileViewModel
import com.example.stageconnect.presentation.screens.search.components.CustomSearchBar
import com.example.stageconnect.ui.theme.BackgroundGray

@Composable
fun EstablishmentStudentScreen(modifier: Modifier = Modifier,
                            profileViewModel: ProfileViewModel,
                               establishmentViewModel: EstablishmentViewModel = hiltViewModel(),
                            onFilterClick: () -> Unit,
                            onNavigate: () -> Unit
) {

    val filteredItems by establishmentViewModel.filteredItems.observeAsState()
    val students by establishmentViewModel.students.observeAsState(initial = emptyList())
    var searchedText by remember { mutableStateOf("") }
    val isLoading = remember { mutableStateOf(false) }

    val getEstablishmentStudentResult by establishmentViewModel.getAllEstablishmentStudentsResult.observeAsState()

    LaunchedEffect(Unit) {
        establishmentViewModel.getAllEstablishmentStudents()
    }

    ObserveResult(
        result = getEstablishmentStudentResult,
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
                        establishmentViewModel.applyFilter(searchedText)
                    }){
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            if (students.isEmpty()){
                NotFound(showMessage = false)
            }else{
                if (filteredItems?.isNotEmpty()!!){
                    LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(24.dp),
                        contentPadding = PaddingValues(end = 10.dp, start = 10.dp, top = 24.dp, bottom = 8.dp)
                    ) {

                        item {
                            filteredItems?.forEach { item ->
                                CustomEstablishmentStudentCard(studentDto = item, circleShape = true){ student ->
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