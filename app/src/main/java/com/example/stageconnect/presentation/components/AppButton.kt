package com.example.stageconnect.presentation.components

import android.media.Image
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.stageconnect.R
import com.example.stageconnect.ui.theme.Blue
import kotlinx.coroutines.launch

@Composable
fun AppButton(modifier: Modifier = Modifier.fillMaxWidth(fraction = 0.8f).padding(vertical = 16.dp),
              fontWeight: FontWeight = FontWeight.Normal,
              text: String,
              withElevation: Boolean = true,
              containerColor: Color = Blue,
              contentColor: Color = Color.White,
              icon: Int = -1,
              onClick : ()->Unit) {
    Button(
        onClick = onClick,
        modifier = modifier,
        shape = RoundedCornerShape(24.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = containerColor,
            contentColor = contentColor
        ),
        elevation = ButtonDefaults.buttonElevation(
            defaultElevation = if (withElevation) 2.dp else 0.dp,
            pressedElevation = if (withElevation) 4.dp else 0.dp
        )
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (icon != -1){
                Image(
                    painter = painterResource(id = icon),
                    contentDescription = "icon",
                )
                Spacer(modifier = Modifier.width(16.dp))
            }
            Text(
                text = text,
                fontWeight = fontWeight
            )
        }
    }
}