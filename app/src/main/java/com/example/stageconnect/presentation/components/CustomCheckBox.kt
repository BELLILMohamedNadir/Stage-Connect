package com.example.stageconnect.presentation.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import com.example.stageconnect.ui.theme.Blue

@Composable
fun CustomCheckbox(
    label: String,
    modifier: Modifier = Modifier,
    isEnabled: Boolean = true,
    isChecked: Boolean = false,
    labelSize: TextUnit = 16.sp,
    textColor: Color = Color.Black,
    onCheckedChange: (Boolean) -> Unit = {}
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.fillMaxWidth()
    ) {
        Checkbox(
            checked = isChecked,
            onCheckedChange = {
                if (isEnabled) onCheckedChange(it)
            },
            colors = CheckboxDefaults.colors(
                checkedColor = Blue,
                uncheckedColor = Blue,
                disabledCheckedColor = Blue
            ),
            enabled = isEnabled
        )

        Text(
            text = label,
            fontSize = labelSize,
            fontWeight = FontWeight.W500,
            color = textColor
        )
    }
}
