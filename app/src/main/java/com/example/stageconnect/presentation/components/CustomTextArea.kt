package com.example.stageconnect.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.stageconnect.ui.theme.BackgroundGray
import com.example.stageconnect.ui.theme.GrayFont

@Composable
fun CustomTextArea(
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    label: String = "",
    defaultText: String = "",
    maxChars: Int = 200,
    fraction: Float = 0.3f,
    onValueChange: (String) -> Unit
) {
    var text by remember { mutableStateOf(defaultText) }
    val remainingChars = maxChars - text.length
    val warningColor = if (remainingChars <= 10) Color.Red else GrayFont

    Column(modifier = modifier.fillMaxWidth()) {
        TextField(
            value = text,
            onValueChange = {
                if (it.length <= maxChars) {
                    text = it
                    onValueChange(it)
                }
            },
            label = { Text(text = label, color = GrayFont) },
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(min = 250.dp)
                .fillMaxHeight(fraction = fraction)
                .clip(RoundedCornerShape(15.dp))
                .background(BackgroundGray),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = BackgroundGray,
                unfocusedContainerColor = BackgroundGray,
                disabledContainerColor = BackgroundGray,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledTextColor = Color.Black,
                cursorColor = warningColor,
                focusedTextColor = Color.Black,
                unfocusedTextColor = Color.Black,
            ),
            singleLine = false,
            shape = RoundedCornerShape(15.dp),
            enabled = enabled
        )

        Text(
            text = "${text.length} / $maxChars",
            modifier = Modifier.align(Alignment.End).padding(top = 4.dp),
            color = warningColor,
            fontSize = 12.sp
        )
    }
}

