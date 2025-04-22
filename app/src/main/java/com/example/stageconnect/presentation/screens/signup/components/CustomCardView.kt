package com.example.stageconnect.presentation.screens.signup.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.ui.draw.clip
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.stageconnect.R
import com.example.stageconnect.ui.theme.Blue
import com.example.stageconnect.ui.theme.BlueBackground
import com.example.stageconnect.ui.theme.BlueFont
import com.example.stageconnect.ui.theme.Gray
import com.example.stageconnect.ui.theme.GrayFont
import com.example.stageconnect.ui.theme.LibreBaskerVilleBold

@Composable
fun CustomCardView(modifier: Modifier = Modifier,
                   index : Int,
                   selectedIndex: Boolean,
                   icon: Int,
                   title: String,
                   description: String,
                   onClick: () -> Unit) {
    Row(
        modifier = modifier
            .height(80.dp)
            .width(290.dp)
            .clip(RoundedCornerShape(30.dp))
            .background(color = if(selectedIndex) BlueBackground else Color.White)
            .border(width = 1.dp, color = GrayFont, shape = RoundedCornerShape(30.dp))
            .padding(end = 5.dp, start = 10.dp)
            .clickable {
                onClick() },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            modifier = Modifier.size(50.dp),
            painter = painterResource(id = icon),
            contentDescription = "icon",
            contentScale = ContentScale.Crop)
        Spacer(modifier = Modifier.width(10.dp))
        Column {
            Text(
                text = title,
                fontFamily = LibreBaskerVilleBold,
                fontSize = 16.sp,
                color = Color.Black,
                fontWeight = FontWeight.W600,
            )
            Spacer(modifier = Modifier.height(5.dp))
            Text(
                text = description,
                fontSize = 13.sp,
                fontFamily = LibreBaskerVilleBold,
                fontWeight = FontWeight.W400,
                color = GrayFont
            )
        }
    }
}