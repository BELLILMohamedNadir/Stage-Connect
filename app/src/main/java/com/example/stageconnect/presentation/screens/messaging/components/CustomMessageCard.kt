package com.example.stageconnect.presentation.screens.messaging.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.stageconnect.data.dtos.MessageDto
import com.example.stageconnect.ui.theme.Blue
import com.example.stageconnect.ui.theme.GrayFont

@Composable
fun CustomMessageCard(
    modifier: Modifier = Modifier,
    userId: Long,
    state: String = "Delivered",
    messageDto: MessageDto) {

    var showState by remember { mutableStateOf(false) }

    Column (modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Text(text = formatTimeBasedOnDate(messageDto.timestamp ?: ""), color = GrayFont, fontSize = 12.sp)
        Row(
            modifier = modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = if (messageDto.senderId == userId) Arrangement.End else Arrangement.Start
        ) {
            Column(
                modifier = Modifier,
                horizontalAlignment = if (messageDto.senderId == userId) Alignment.End else Alignment.Start,
                verticalArrangement = Arrangement.Center
            ) {
                Card(
                    modifier = Modifier.clip(
                        RoundedCornerShape(32.dp)
                    ).background(color = Blue).clickable {
                        showState = !showState
                    }
                ){
                    Box(contentAlignment = Alignment.Center, modifier = Modifier.background(Blue).padding(vertical = 8.dp, horizontal = 12.dp)){
                        Text(text = messageDto.content, color = Color.White)
                    }
                }
                if (showState)
                    Text(text = state, modifier = Modifier.padding(horizontal = 12.dp), fontSize = 12.sp)
            }
        }
    }

}