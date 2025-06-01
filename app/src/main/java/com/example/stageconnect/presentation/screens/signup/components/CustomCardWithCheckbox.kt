package com.example.stageconnect.presentation.screens.signup.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.stageconnect.presentation.components.CustomCheckbox
import com.example.stageconnect.ui.theme.BlueBackground
import com.example.stageconnect.ui.theme.GrayFont

@Composable
fun CustomCardWithCheckbox(modifier: Modifier = Modifier,
                           index: Int,
                           selectedIndex: Boolean,
                           label: String,
                           onClick : () -> Unit) {
    Row(
        modifier = modifier
            .height(50.dp)
            .fillMaxWidth(fraction = 0.95f)
            .clip(RoundedCornerShape(10.dp))
            .background(color = if(selectedIndex) BlueBackground else Color.White)
            .border(width = 1.dp, color = GrayFont, shape = RoundedCornerShape(10.dp))
            .padding(end = 5.dp, start = 10.dp)
            .clickable {
                onClick()
                       },
        verticalAlignment = Alignment.CenterVertically
    ){
        CustomCheckbox(isChecked = selectedIndex, label = label, textColor = GrayFont){
            onClick()
        }
    }
}