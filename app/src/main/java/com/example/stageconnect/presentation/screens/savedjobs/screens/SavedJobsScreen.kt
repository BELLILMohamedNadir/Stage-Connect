package com.example.stageconnect.presentation.screens.savedjobs.screens

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.stageconnect.R
import com.example.stageconnect.presentation.components.CustomOfferCard
import com.example.stageconnect.presentation.components.ErrorMessage
import com.example.stageconnect.presentation.components.NotFound
import com.example.stageconnect.presentation.components.ObserveResult
import com.example.stageconnect.presentation.screens.offer.viewmodel.JobDetailsViewModel
import com.example.stageconnect.presentation.screens.savedjobs.components.CustomDialog
import com.example.stageconnect.presentation.screens.search.components.CustomSearchBar
import com.example.stageconnect.presentation.viewmodels.OfferViewModel
import com.example.stageconnect.ui.theme.LibreBaskerVilleBold

@Composable
fun SavedJobsScreen(modifier: Modifier = Modifier,
                    filterViewModel: OfferViewModel = hiltViewModel(),
                    offerViewModel: OfferViewModel = hiltViewModel(),
                    jobDetailsViewModel: JobDetailsViewModel,
                    onFilterClick: () -> Unit,
                    onOfferCardClick: () -> Unit,
                    onDismiss: () -> Unit
) {

    val filteredItems by filterViewModel.filteredOffers.observeAsState(initial = emptyList())
    var searchedText by remember { mutableStateOf("") }
    val isLoading = remember { mutableStateOf(false) }
    val isButtonLoading = remember { mutableStateOf(false) }
    var showDialog by remember { mutableStateOf(false) }

    val context = LocalContext.current
    val unSavedMessage = stringResource(R.string.un_saved_offer)

    val getAllSavedOfferResult by offerViewModel.getAllSavedOfferResult.observeAsState()
    val unSavedOfferResult by offerViewModel.saveOfferResult.observeAsState()

    LaunchedEffect(Unit) {
        offerViewModel.getAllSavedOffer()
    }

    ObserveResult(
        result = getAllSavedOfferResult,
        onLoading = {isLoading.value = true},
        onSuccess = {
            isLoading.value = false
        },
        onError = {
            isLoading.value = false
            ErrorMessage.Show(stringResource(R.string.error_occurred))
        }
    )

    ObserveResult(
        result = unSavedOfferResult,
        onLoading = {isButtonLoading.value = true},
        onSuccess = {
            isButtonLoading.value = false
            showDialog = false
            offerViewModel.saveOnlySavedOffers()
        },
        onError = {
            isButtonLoading.value = false
            ErrorMessage.Show(stringResource(R.string.error_occurred))
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
                    CustomSearchBar (onFilterClick = {onFilterClick() }){
                        searchedText = it
                        filterViewModel.applyFilter(searchedText)
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            //Body
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
                //offers list
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(24.dp)
                    ){
                        items(filteredItems.size){ index ->
                            filteredItems[index].isSaved = true
                            CustomOfferCard(
                                offerDto = filteredItems[index],
                                isSaveIconDisable = true,
                                onSavedClick = {offer ->
                                jobDetailsViewModel.setOffer(offer)
                                showDialog = true
                            }
                            ){ offer ->
                                jobDetailsViewModel.setOffer(offer)
                                onOfferCardClick()
                            }
                        }
                    }
                    if (showDialog){
                        CustomDialog(
                            label = stringResource(R.string.remove_from_saved),
                            offer = jobDetailsViewModel.offer.value!!,
                            isLoading = isButtonLoading,
                            onDismiss = {showDialog = false}
                        ) { offer ->
                            offerViewModel.saveOffer(offerDto = offer, unSavedOfferMessage = unSavedMessage, context = context)
                        }
                    }
                }
            }else{
                NotFound()
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