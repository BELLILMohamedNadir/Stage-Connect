package com.example.stageconnect.presentation.components

import com.example.stageconnect.ui.theme.Blue
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun CustomToggleSwitch(
    modifier: Modifier = Modifier,
    isChecked: Boolean = false,
    activeColor: Color = Blue,
    inactiveColor: Color = Color.LightGray,
    thumbColor: Color = Color.White,
    label: String = "",
    onCheckedChange: (Boolean) -> Unit,
) {
    var checked by remember { mutableStateOf(isChecked) }

    Row(
        modifier = modifier
            .padding(vertical = 8.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        if (label.isNotEmpty()) {
            Text(
                text = label,
                color = Color.Black,
                fontSize = 16.sp
            )
            Spacer(modifier = Modifier.width(10.dp))
        }

        Switch(
            checked = checked,
            onCheckedChange = {
                checked  = it
                onCheckedChange(it)
            },
            colors = SwitchDefaults.colors(
                checkedThumbColor = thumbColor,
                uncheckedThumbColor = thumbColor,
                checkedTrackColor = activeColor,
                uncheckedTrackColor = inactiveColor,
                uncheckedBorderColor = Color.Transparent
            )
        )
    }
}

