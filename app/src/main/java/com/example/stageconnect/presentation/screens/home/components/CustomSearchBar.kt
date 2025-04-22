package com.example.stageconnect.presentation.screens.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import com.example.stageconnect.ui.theme.BackgroundGray
import com.example.stageconnect.ui.theme.BackgroundGray_
import com.example.stageconnect.ui.theme.Blue
import com.example.stageconnect.ui.theme.GrayFont
import com.example.stageconnect.ui.theme.LibreBaskerVilleBold

@Composable
fun CustomSearchBar(modifier: Modifier = Modifier, onFilterClick: () -> Unit, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .height(50.dp)
            .width(320.dp)
            .clip(shape = RoundedCornerShape(10.dp))
            .background(color = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
    ){
        Row(modifier = Modifier
            .fillMaxSize()
            .background(color = BackgroundGray)
            .clickable { onClick() }
            .padding(2.dp)
            ,
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceAround){
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = {onClick()}) {
                    Icon(painter = painterResource(R.drawable.ic_search), contentDescription = "search icon", tint = Color.Unspecified)
                }
                Text(text = stringResource(R.string.search_for_a_job_or_company),
                    fontFamily = LibreBaskerVilleBold,
                    fontWeight = FontWeight.W500,
                    fontSize = 13.sp,
                    color = GrayFont,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    textAlign = TextAlign.Center)
            }
            IconButton(onClick = {onFilterClick()}) {
                Icon(painter = painterResource(R.drawable.ic_filter), contentDescription = "filter icon", tint = Color.Unspecified)
            }
        }
    }
}