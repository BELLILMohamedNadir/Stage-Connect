package com.example.stageconnect.presentation.screens.applicationstatus.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.stageconnect.R
import com.example.stageconnect.domain.model.enums.STATUS
import com.example.stageconnect.presentation.components.ProfileImage
import com.example.stageconnect.ui.theme.BackgroundGray
import com.example.stageconnect.ui.theme.BlackFont
import com.example.stageconnect.ui.theme.Blue
import com.example.stageconnect.ui.theme.GrayFont
import com.example.stageconnect.ui.theme.GreenBackground
import com.example.stageconnect.ui.theme.GreenFont
import com.example.stageconnect.ui.theme.LibreBaskerVilleBold
import com.example.stageconnect.ui.theme.RedBackground
import com.example.stageconnect.ui.theme.RedFont
import com.example.stageconnect.ui.theme.SoftBlue

@Composable
fun CustomCard(modifier: Modifier = Modifier,
               label: String,
               onIconCLick: () -> Unit) {

    Row(
        modifier = modifier.fillMaxWidth(fraction = 1f)
            .height(80.dp)
            .clip(RoundedCornerShape(15.dp))
            .background(color = BackgroundGray, shape = RoundedCornerShape(15.dp))
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        Row (
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ){
            Text(
                text = label,
                fontFamily = LibreBaskerVilleBold,
                fontWeight = FontWeight.W600,
                fontSize = 16.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                color = Color.Black)

            IconButton(onClick = {
                onIconCLick()
            },
                modifier = Modifier.align(Alignment.CenterVertically)) {
                Icon(painter = painterResource(R.drawable.ic_arrow_right),
                    contentDescription = "arrow right icon",
                    tint = Color.Unspecified)
            }

        }
    }

}