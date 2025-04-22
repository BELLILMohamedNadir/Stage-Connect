package com.example.stageconnect.presentation.screens.filter.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonColors
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.stageconnect.R
import com.example.stageconnect.ui.theme.BackgroundGray_
import com.example.stageconnect.ui.theme.Blue
import com.example.stageconnect.ui.theme.LibreBaskerVilleBold
import com.example.stageconnect.ui.theme.SoftBlue

@Composable
fun CustomRadioFilterCard(
    criterion: String,
    options: List<String>,
    selectedOption: String,
    onOptionSelected: (String) -> Unit,
    isExpanded: Boolean,
    onExpanded: () -> Unit
) {
    Column(
        modifier = Modifier
            .heightIn(min = 50.dp)
            .fillMaxWidth(0.95f)
            .clip(RoundedCornerShape(20.dp))
            .border(1.dp, BackgroundGray_, RoundedCornerShape(20.dp))
            .padding(horizontal = 24.dp, vertical = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = criterion,
                fontFamily = LibreBaskerVilleBold,
                fontWeight = FontWeight.W600,
                fontSize = 20.sp,
                textAlign = TextAlign.Center
            )
            IconButton(
                onClick = onExpanded,
                modifier = Modifier.size(18.dp)
            ) {
                Icon(
                    painter = if (!isExpanded) painterResource(R.drawable.ic_arrow_down)
                    else painterResource(R.drawable.ic_arrow_up),
                    contentDescription = "Expanded Icon",
                    tint = Blue
                )
            }
        }
        if (isExpanded) {
            Spacer(modifier = Modifier.height(30.dp))
            Spacer(
                modifier = Modifier
                    .fillMaxWidth(0.95f)
                    .height(1.dp)
                    .background(color = BackgroundGray_)
            )
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                options.forEach { option ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { onOptionSelected(option) }
                    ) {
                        RadioButton(
                            selected = selectedOption == option,
                            onClick = { onOptionSelected(option) },
                            colors = RadioButtonDefaults.colors(
                                selectedColor = Blue,
                                unselectedColor = Blue
                            ),
                        )
                        Text(
                            text = option,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.W500,
                            color = Color.Black
                        )
                    }
                }
            }
        }
    }
}




