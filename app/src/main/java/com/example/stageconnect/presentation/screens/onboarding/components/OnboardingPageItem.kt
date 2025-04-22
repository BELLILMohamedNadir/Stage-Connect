package com.example.stageconnect.presentation.screens.onboarding.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.stageconnect.domain.model.OnboardingPage
import com.example.stageconnect.ui.theme.Blue
import com.example.stageconnect.ui.theme.ReemKufiInkRegular

@Composable
fun OnboardingPageItem(page: OnboardingPage) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = page.imageRes),
            contentDescription = page.title,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxHeight(fraction = 0.6f)
                .padding(16.dp)
        )

        Text(
            text = page.title,
            fontFamily = ReemKufiInkRegular,
            fontWeight = FontWeight.W700,
            textAlign = TextAlign.Center,
            fontSize = 32.sp,
            color = Blue,
            modifier = Modifier.padding(2.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = page.description,
            fontWeight = FontWeight.W500,
            textAlign = TextAlign.Center,
            fontSize = 20.sp,
            modifier = Modifier.padding(2.dp)
        )
    }
}
