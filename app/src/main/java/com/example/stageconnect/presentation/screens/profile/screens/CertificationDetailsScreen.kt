package com.example.stageconnect.presentation.screens.profile.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.stageconnect.R
import com.example.stageconnect.data.dtos.CertificationDto
import com.example.stageconnect.data.dtos.WorkExperienceDto
import com.example.stageconnect.presentation.components.CustomCircularProgressIndicator
import com.example.stageconnect.presentation.components.ObserveResult
import com.example.stageconnect.presentation.components.ProfileImage
import com.example.stageconnect.presentation.screens.profile.components.DetailsCard
import com.example.stageconnect.presentation.screens.profile.viewmodels.CertificationViewModel
import com.example.stageconnect.presentation.screens.profile.viewmodels.ProfileViewModel
import com.example.stageconnect.presentation.screens.profile.viewmodels.WorkExperienceViewModel
import com.example.stageconnect.ui.theme.BackgroundGray_
import com.example.stageconnect.ui.theme.GrayFont

@Composable
fun CertificationDetailsScreen(modifier: Modifier = Modifier,
                  certificationViewModel: CertificationViewModel,
                               profileViewModel: ProfileViewModel,
                  onClick: () -> Unit) {

    val getAllCertificationResult by certificationViewModel.getAllCertificationResult.observeAsState()
    val isLoading = remember { mutableStateOf(false) }
    val data by certificationViewModel.data.observeAsState(emptyList())
    val user = profileViewModel.user.value!!


    LaunchedEffect(Unit) {
        certificationViewModel.getAllCertification()
    }

    ObserveResult(
        result = getAllCertificationResult,
        onLoading = {isLoading.value = true},
        onSuccess = {
            isLoading.value = false
        },
        onError = {
            isLoading.value = false
        }
    )
    Column(modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally) {
        Row(modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween){
            Row(modifier = Modifier.weight(1f).padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically) {
                ProfileImage(user.photo, 80.dp)
                Spacer(modifier = Modifier.width(10.dp))
                Column {
                    Text(
                        text = "${user.name} ${user.firstName}",
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 18.sp,
                        color = Color.Black,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis)
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = user.currentPosition ?: "",
                        fontWeight = FontWeight.Normal,
                        fontSize = 16.sp,
                        maxLines = 1,
                        color = GrayFont,
                        overflow = TextOverflow.Ellipsis)
                }
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Spacer(modifier = Modifier.fillMaxWidth(fraction = 0.9f).height(1.dp).background(color = BackgroundGray_))
        Spacer(modifier = Modifier.height(10.dp))
        if (isLoading.value){
            Column(
                modifier = modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                CircularProgressIndicator()
            }
        }else{
            Column(
                modifier = Modifier.fillMaxHeight(fraction = 0.9f)
                    .fillMaxWidth(fraction = 0.9f)
                    .background(Color.White, shape = RoundedCornerShape(12.dp))
                    .border(width = 1.dp, color = BackgroundGray_, shape = RoundedCornerShape(12.dp))
                    .padding(vertical = 16.dp, horizontal = 8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                if (data.isEmpty()){
                    Column(modifier = Modifier.fillMaxSize().clickable { onClick() },
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center) {
                        Image(painter = painterResource(R.drawable.add_item), contentDescription = "add item",
                            modifier = Modifier.size(250.dp))
                        Spacer(modifier = modifier.height(10.dp))
                        Text(text = stringResource(R.string.add_certification), fontWeight = FontWeight.Bold, fontSize = 18.sp)
                    }
                }else{
                    Text(text = stringResource(R.string.certifications), fontWeight = FontWeight.Bold, modifier = Modifier.align(Alignment.Start))
                    Spacer(modifier = Modifier.height(4.dp))
                    LazyColumn(
                        modifier = modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ){
                        itemsIndexed(data) { index, it ->
                            var date = it.dateOfIssue
                            if ((it.expirationDate?:"").isNotBlank()) {
                                date = "${it.dateOfIssue} to ${it.expirationDate}"
                            }
                            DetailsCard(title = it.title, detail = date, index = index) {
                                certificationViewModel.setCertification(data[it])
                                onClick()
                            }
                        }
                    }
                }
            }
        }
    }
}