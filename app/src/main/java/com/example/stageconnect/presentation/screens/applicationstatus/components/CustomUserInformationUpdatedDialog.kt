package com.example.stageconnect.presentation.screens.applicationstatus.components


import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.stageconnect.R
import com.example.stageconnect.data.dtos.ApplicationDto
import com.example.stageconnect.data.dtos.OfferDto
import com.example.stageconnect.presentation.components.AppButton
import com.example.stageconnect.presentation.components.CustomEditText
import com.example.stageconnect.presentation.components.CustomOfferCard
import com.example.stageconnect.ui.theme.BackgroundGray_
import com.example.stageconnect.ui.theme.Blue
import com.example.stageconnect.ui.theme.RedFont
import com.example.stageconnect.ui.theme.SoftBlue


@Composable
fun CustomUserInformationUpdatedDialog(modifier: Modifier = Modifier,
                 label: String,
                 onDismiss: ()->Unit,
                 onNavigate: () -> Unit) {
    var text by remember { mutableStateOf("") }
    val context = LocalContext.current
    val message = stringResource(R.string.add_reason)
    Dialog(
        onDismissRequest = { onDismiss() },
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.BottomCenter
        ) {
            Column(
                modifier = modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .background(
                        Color.White,
                        shape = RoundedCornerShape(topStart = 42.dp, topEnd = 42.dp)
                    )
                    .padding(top = 30.dp, end = 8.dp, start = 8.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    text = label,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.W700,
                    color = Color.Black,
                    textAlign = TextAlign.Center
                )
                // ---------------------------------------
                Spacer(modifier = Modifier.height(3.dp))
                Spacer(
                    modifier = Modifier
                        .height(1.dp)
                        .fillMaxWidth(0.9f)
                        .background(color = BackgroundGray_)
                )
                Spacer(modifier = Modifier.height(45.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(end = 16.dp, start = 16.dp, top = 20.dp, bottom = 16.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    AppButton(
                        modifier = Modifier.weight(1f),
                        text = stringResource(R.string.cancel),
                        contentColor = Blue,
                        containerColor = SoftBlue
                    ) {
                        onDismiss()
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    AppButton(
                        modifier = Modifier.weight(1f),
                        text = stringResource(R.string.update),
                        containerColor = Blue,
                    ) {
                        onNavigate()
                    }
                }
            }
        }
    }
}