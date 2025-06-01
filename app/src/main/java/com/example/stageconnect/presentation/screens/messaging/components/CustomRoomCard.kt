package com.example.stageconnect.presentation.screens.messaging.components

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.stageconnect.R
import com.example.stageconnect.data.dtos.RoomDto
import com.example.stageconnect.domain.DateComparator
import com.example.stageconnect.presentation.components.ProfileImage
import com.example.stageconnect.ui.theme.BlackFont
import com.example.stageconnect.ui.theme.Gray
import com.example.stageconnect.ui.theme.GrayFont
import com.example.stageconnect.ui.theme.LibreBaskerVilleBold
import java.time.Duration
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

@Composable
fun CustomRoomCard(modifier: Modifier = Modifier,
                   roomDto: RoomDto,
                   userId: Long,
                   onNavigate: (RoomDto) -> Unit = {}) {

    Column(
        modifier = modifier
            .height(80.dp)
            .fillMaxWidth(fraction = 0.9f)
            .background(Color.White)
            .clickable {
                onNavigate(roomDto)
            },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(modifier = Modifier.weight(1f)){
            //logo
            Column(modifier = Modifier.align(alignment = Alignment.Top),
                verticalArrangement = Arrangement.Top) {
                ProfileImage(roomDto.logo, 64.dp)
            }

            Spacer(modifier = Modifier.width(10.dp))
            //sender and last message
            Column(
                modifier = Modifier.fillMaxHeight(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Column (
                    modifier = Modifier.align(Alignment.Start)
                ){
                    Row(modifier = Modifier.fillMaxWidth()) {
                        Text(
                            modifier = Modifier.weight(1f),
                            text = roomDto.sender,
                            fontFamily = LibreBaskerVilleBold,
                            fontWeight = FontWeight.W600,
                            fontSize = 16.sp,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            color = Color.Black)
                    }
                    Row(modifier = Modifier.fillMaxWidth()) {
                        Text(
                            modifier = Modifier.weight(1f),
                            text = buildAnnotatedString {
                                if (roomDto.lastMessage.isNotEmpty()){
                                    if (roomDto.senderId == userId)
                                        append("${stringResource(R.string.you)}: ")
                                    append(roomDto.lastMessage)
                                }else{
                                    append(stringResource(R.string.send_first_message))
                                }
                            },
                            fontFamily = LibreBaskerVilleBold,
                            fontWeight = FontWeight.W500,
                            fontSize = 13.sp,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            color = GrayFont)
                        Spacer(modifier = Modifier.width(3.dp))
                        Text(
                            text = formatTimeBasedOnDate(roomDto.lastMessageTime?:""),
                            fontFamily = LibreBaskerVilleBold,
                            fontWeight = FontWeight.W600,
                            fontSize = 12.sp,
                            maxLines = 1,
                            color = Color.Black)

                    }
                }
            }
        }
    }
}

fun formatTimeBasedOnDate(time: String): String {
    if (time.isNotBlank()){
        val pastTime = stringToLocalDateTime(time)
        val now = LocalDateTime.now()

        return when {
            pastTime.toLocalDate() == now.toLocalDate() -> {
                // Same day -> show time as HH:mm
                pastTime.format(DateTimeFormatter.ofPattern("HH:mm"))
            }
            pastTime.toLocalDate().dayOfYear == now.toLocalDate().dayOfYear - 1 -> {
                // If it's the previous day -> show day and time
                pastTime.format(DateTimeFormatter.ofPattern("E, HH:mm", Locale.FRENCH)) // e.g., Lun, 11:11
            }
            pastTime.toLocalDate().month == now.toLocalDate().month -> {
                // Same month, but different day -> show weekday and time
                pastTime.format(DateTimeFormatter.ofPattern("E, HH:mm", Locale.FRENCH)) // e.g., Lun, 11:11
            }
            else -> {
                // Different month -> show day of the month and month name
                pastTime.format(DateTimeFormatter.ofPattern("d MMM", Locale.FRENCH)) // e.g., 21 Mai
            }
        }
    }else
        return ""
}

private fun stringToLocalDateTime(dateString: String): LocalDateTime {
    return try {
        // Try to parse with the full format, including fractional seconds
        val formatterWithFraction = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS", Locale.FRENCH)
        LocalDateTime.parse(dateString, formatterWithFraction)
    } catch (e: Exception) {
        // If parsing with fractional seconds fails, try parsing without fractional seconds
        val formatterWithoutFraction = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss", Locale.FRENCH)
        LocalDateTime.parse(dateString, formatterWithoutFraction)
    }
}