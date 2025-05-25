package com.example.stageconnect.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.stageconnect.R
import com.example.stageconnect.ui.theme.GrayFont
import com.example.stageconnect.ui.theme.LibreBaskerVilleBold

@Composable
fun NoDataFound(modifier: Modifier = Modifier, showMessage: Boolean = true) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ){
        Image(painter = painterResource(R.drawable.bad_request),
            contentDescription = "bad request image",
            modifier = Modifier.height(320.dp).width(360.dp),
            contentScale = ContentScale.Crop)

        Spacer(modifier = Modifier.height(20.dp))
        Text(text = stringResource(R.string.not_found),
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
            fontWeight = FontWeight.W700,
            fontSize = 24.sp,
            color = Color.Black,
            textAlign = TextAlign.Center
        )

        if (showMessage){
            Spacer(modifier = Modifier.height(30.dp))
            Text(text = stringResource(R.string.not_found_error_message),
                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
                fontFamily = LibreBaskerVilleBold,
                fontWeight = FontWeight.W500,
                fontSize = 16.sp,
                color = GrayFont,
                textAlign = TextAlign.Center
            )
        }
    }
}