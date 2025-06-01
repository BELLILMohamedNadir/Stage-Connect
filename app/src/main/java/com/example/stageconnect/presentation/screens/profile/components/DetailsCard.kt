package com.example.stageconnect.presentation.screens.profile.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.stageconnect.presentation.screens.messaging.components.formatTimeBasedOnDate
import com.example.stageconnect.ui.theme.BackgroundGray_
import com.example.stageconnect.ui.theme.GrayFont
import com.example.stageconnect.ui.theme.LibreBaskerVilleBold

@Composable
fun DetailsCard(modifier: Modifier = Modifier,
                title: String?,
                detail: String?,
                index: Int = 0,
                onClick: (Int) -> Unit) {
    Column(
        modifier = modifier
            .height(80.dp)
            .fillMaxWidth(fraction = 0.95f)
            .background(Color.White, shape = RoundedCornerShape(12.dp))
            .clickable {
               onClick(index)
            }
            .border(width = 1.dp, color = BackgroundGray_, shape = RoundedCornerShape(12.dp))
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceAround
    ) {
        Text(
            modifier = Modifier.align(Alignment.Start),
            text = title?:"",
            fontFamily = LibreBaskerVilleBold,
            fontWeight = FontWeight.W500,
            fontSize = 14.sp,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            color = Color.Black
        )
        Text(
            modifier = Modifier.align(Alignment.End),
            text = detail?:"",
            fontFamily = LibreBaskerVilleBold,
            fontWeight = FontWeight.W600,
            fontSize = 10.sp,
            maxLines = 1,
            color = Color.Black)
    }
}