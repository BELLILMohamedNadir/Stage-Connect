package com.example.stageconnect.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.stageconnect.ui.theme.Blue
import com.example.stageconnect.ui.theme.LibreBaskerVilleBold

@Composable
fun CustomCardOption(modifier: Modifier = Modifier, index: Int = 0, option: String, isSelected : Boolean = false, onClick: () -> Unit = {}) {
    Card(
        modifier = modifier
            .height(30.dp)
            .clip(shape = RoundedCornerShape(20.dp))
            .border(width = 1.dp, color = Blue, shape = RoundedCornerShape(20.dp))
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
    ){
        Box(modifier = Modifier
            .fillMaxHeight()
            .background(color = if (isSelected) Blue else Color.White)
            .padding(horizontal = 10.dp),
            contentAlignment = Alignment.Center){
            Text(text = option,
                fontFamily = LibreBaskerVilleBold,
                fontWeight = FontWeight.W600,
                fontSize = 13.sp,
                color = if (isSelected) Color.White else Blue,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                textAlign = TextAlign.Center)
        }
    }
}