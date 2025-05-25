package com.example.stageconnect.presentation.screens.search.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.stageconnect.R
import com.example.stageconnect.presentation.components.CustomOfferCard
import com.example.stageconnect.presentation.components.NotFound
import com.example.stageconnect.presentation.screens.offer.viewmodel.JobDetailsViewModel
import com.example.stageconnect.presentation.screens.search.components.CustomSearchBar
import com.example.stageconnect.presentation.viewmodels.OfferViewModel
import com.example.stageconnect.ui.theme.LibreBaskerVilleBold

@Composable
fun SearchScreen(modifier: Modifier = Modifier,
                 filterViewModel: OfferViewModel = hiltViewModel(),
                 jobDetailsViewModel: JobDetailsViewModel,
                 onFilterClick: () -> Unit,
                 onOfferCardClick: () -> Unit,
                 onDismiss: () -> Unit
                 ) {

    val filteredItems by filterViewModel.filteredOffers.observeAsState(initial = emptyList())
    var searchedText by remember { mutableStateOf("") }


    Column(modifier = modifier.fillMaxSize().background(Color.White)) {
        //Header
        Row(
            modifier = Modifier.fillMaxWidth().padding(top = 12.dp, start = 4.dp, end = 24.dp),
            verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = { onDismiss() }) {
                Icon(
                    painter = painterResource(R.drawable.ic_arrow_back),
                    contentDescription = "navigationIcon"
                )
            }
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
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ){
                    items(filteredItems.size){ index ->
                        CustomOfferCard(offerDto = filteredItems[index]){ offer ->
                            jobDetailsViewModel.setOffer(offer)
                            onOfferCardClick()
                        }
                    }
                }
            }
        }else{
            NotFound()
        }
    }
}