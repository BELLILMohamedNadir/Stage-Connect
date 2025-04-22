package com.example.stageconnect.presentation.screens.appstart

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
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
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.stageconnect.R
import com.example.stageconnect.presentation.components.AppButton
import com.example.stageconnect.presentation.components.AppLogo
import com.example.stageconnect.ui.theme.Blue
import com.example.stageconnect.ui.theme.LibreBaskerVilleBold
import com.example.stageconnect.ui.theme.ReemKufiInkRegular

@Composable
fun AppStartScreen(modifier: Modifier = Modifier, onClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(4.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {


        AppLogo()

        Column {
            Text(
                text =buildAnnotatedString {
                    withStyle(SpanStyle(color = Color.Black)){
                        append(stringResource(R.string.hire))
                    }
                    append(" ")
                    withStyle(SpanStyle(color = Blue)){
                        append(stringResource(R.string.talent))
                    }
                },
                fontFamily = LibreBaskerVilleBold,
                fontWeight = FontWeight.W700,
                textAlign = TextAlign.Center,
                fontSize = 40.sp,
                modifier = Modifier.padding(2.dp)
            )
            Text(
                text =buildAnnotatedString {
                    withStyle(SpanStyle(color = Color.Black)){
                        append(stringResource(R.string.find))
                    }
                    append(" ")
                    withStyle(SpanStyle(color = Blue)){
                        append(stringResource(R.string.success))
                    }
                },
                fontFamily = LibreBaskerVilleBold,
                fontWeight = FontWeight.W700,
                textAlign = TextAlign.Center,
                fontSize = 40.sp,
                modifier = Modifier.padding(2.dp)
            )
        }
        AppButton(modifier = Modifier.fillMaxWidth(fraction = 0.45f).padding(4.dp),
            fontWeight = FontWeight.W700,
            text = stringResource(R.string.get_started),
            onClick = onClick)
    }
}