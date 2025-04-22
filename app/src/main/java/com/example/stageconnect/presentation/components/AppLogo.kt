package com.example.stageconnect.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.stageconnect.R
import com.example.stageconnect.ui.theme.Blue

@Composable
fun AppLogo(modifier: Modifier = Modifier) {
    Column {
        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "logo",
            contentScale = ContentScale.Crop,
            modifier = modifier
                .height(131.dp)
                .width(100.dp)
        )
        Spacer(modifier = Modifier.height(1.dp))
        Text(text = stringResource(R.string.app_name)
            , color = Blue,
            fontWeight = FontWeight.W700)
    }
}