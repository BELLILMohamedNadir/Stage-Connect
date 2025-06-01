package com.example.stageconnect.presentation.screens.profile.components

import android.media.Image
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.stageconnect.R
import com.example.stageconnect.ui.theme.BackgroundGray
import com.example.stageconnect.ui.theme.BackgroundGray_
import com.example.stageconnect.ui.theme.Blue

@Composable
fun CustomCard(modifier: Modifier = Modifier,
               text: String,
               image: Int,
               onCardClick: () -> Unit,
               onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth(fraction = 0.95f)
            .height(50.dp)
            .clip(RoundedCornerShape(36.dp))
            .clickable { onCardClick() },
        elevation = CardDefaults.elevatedCardElevation(4.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth()
                .background(Color.White)
                .border(width = 1.dp, color = BackgroundGray_, shape = RoundedCornerShape(36.dp))
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween) {
            Row(modifier = Modifier.weight(1f),
                verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    painter = painterResource(image),
                    contentDescription = "image",
                    modifier = Modifier.size(20.dp),
                    tint = Blue
                )

                Spacer(modifier = Modifier.width(12.dp))
                Text(text = text,
                    fontWeight = FontWeight.W700,
                    color = Color.Black,
                    overflow = TextOverflow.Ellipsis)
            }
            IconButton(onClick = onClick) {
                Icon(painter = painterResource(R.drawable.ic_more),
                    contentDescription = "Add button",
                    tint = Color.Unspecified)
            }
        }
    }
}