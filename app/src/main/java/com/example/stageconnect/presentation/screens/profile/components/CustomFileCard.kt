package com.example.stageconnect.presentation.screens.profile.components

import android.content.Context
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
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
import com.example.stageconnect.domain.FileUtils
import com.example.stageconnect.ui.theme.BackgroundGray
import com.example.stageconnect.ui.theme.BackgroundGray_
import com.example.stageconnect.ui.theme.Blue
import com.example.stageconnect.ui.theme.GrayFont
import com.example.stageconnect.ui.theme.GreenFont
import com.example.stageconnect.ui.theme.RedBackground
import com.example.stageconnect.ui.theme.RedFont
import com.google.accompanist.placeholder.material.placeholder


@Composable
fun CustomFileCard(
    modifier: Modifier = Modifier,
    context: Context,
    uri: Uri?,
    isApplication: Boolean = false,
    isLoading: Boolean = false,
    onDeleteClick: (Uri) -> Unit,
    onItemClick: (Uri) -> Unit
) {
    if (isLoading || uri == null) {
        // Show shimmer placeholder
        Row(
            modifier = modifier
                .fillMaxWidth()
                .height(80.dp)
                .clip(RoundedCornerShape(15.dp))
                .background(BackgroundGray)
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Spacer(
                modifier = Modifier
                    .size(40.dp)
                    .placeholder(visible = true)
            )
            Spacer(modifier = Modifier.width(10.dp))
            Column(modifier = Modifier.weight(1f)) {
                Spacer(
                    modifier = Modifier
                        .height(16.dp)
                        .fillMaxWidth(0.6f)
                        .placeholder(visible = true)
                )
                Spacer(modifier = Modifier.height(4.dp))
                Spacer(
                    modifier = Modifier
                        .height(12.dp)
                        .fillMaxWidth(0.3f)
                        .placeholder(visible = true)
                )
            }
        }
    } else {
        FileUtils.getFileNameAndSize(context, uri)?.let { (title, size) ->
            Row(
                modifier = modifier
                    .fillMaxWidth()
                    .height(80.dp)
                    .clip(RoundedCornerShape(15.dp))
                    .background(
                        color = if (isApplication) BackgroundGray else RedBackground,
                        shape = RoundedCornerShape(15.dp)
                    )
                    .clickable { onItemClick(uri) }
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                Row(
                    modifier = Modifier
                        .padding(horizontal = 10.dp)
                        .weight(1f)
                ) {
                    Icon(
                        painter = painterResource(R.drawable.ic_pdf),
                        contentDescription = "pdf icon",
                        tint = if (isApplication) Blue else Color.Unspecified
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Column(horizontalAlignment = Alignment.Start) {
                        Text(
                            text = title,
                            fontSize = 16.sp,
                            color = Color.Black,
                            fontWeight = FontWeight.SemiBold,
                            overflow = TextOverflow.Ellipsis
                        )
                        Text(
                            text = "$size Kb",
                            fontSize = 12.sp,
                            color = GrayFont,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }
                if (!isApplication) {
                    IconButton(
                        onClick = { onDeleteClick(uri) },
                        modifier = Modifier.size(16.dp)
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.ic_close),
                            contentDescription = "close icon",
                            tint = RedFont
                        )
                    }
                }
            }
        }
    }
}
