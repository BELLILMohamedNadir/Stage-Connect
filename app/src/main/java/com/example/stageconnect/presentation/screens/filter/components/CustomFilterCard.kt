package com.example.stageconnect.presentation.screens.filter.components

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.stageconnect.R
import com.example.stageconnect.presentation.components.CustomCheckbox
import com.example.stageconnect.ui.theme.BackgroundGray_
import com.example.stageconnect.ui.theme.Blue
import com.example.stageconnect.ui.theme.LibreBaskerVilleBold

@Composable
fun CustomFilterCard(
    index: Int,
    criterion: String,
    criteria: List<String>,
    isCheckedMap: Map<String, MutableState<Boolean>>,
    isExpended: Boolean,
    width: Float = 0.95f,
    onCheck: (Int, String, String, Boolean) -> Unit,
    onExpandToggle: () -> Unit
) {
    Column(
        modifier = Modifier
            .heightIn(min = 50.dp)
            .fillMaxWidth(width)
            .clip(RoundedCornerShape(20.dp))
            .border(1.dp, BackgroundGray_, RoundedCornerShape(20.dp))
            .padding(horizontal = 24.dp, vertical = 16.dp)
            .animateContentSize(
                animationSpec = tween(
                    durationMillis = 300,
                    easing = FastOutSlowInEasing
                )
            ),
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
                onClick = onExpandToggle,
                modifier = Modifier.size(18.dp)
            ) {
                Icon(
                    painter = if (!isExpended) painterResource(R.drawable.ic_arrow_down)
                    else painterResource(R.drawable.ic_arrow_up),
                    contentDescription = "Expanded Icon",
                    tint = Blue
                )
            }
        }

        if (isExpended) {
            Spacer(modifier = Modifier.height(30.dp))
            Spacer(
                modifier = Modifier
                    .fillMaxWidth(0.95f)
                    .height(1.dp)
                    .background(color = BackgroundGray_)
            )

            criteria.forEach { item ->
                val checkedState = isCheckedMap[item] ?: remember { mutableStateOf(false) }

                CustomCheckbox(
                    label = item,
                    isChecked = checkedState.value,
                    onCheckedChange = { checked ->
                        checkedState.value = checked
                        onCheck(index, criterion, item, checked)
                    }
                )
            }
        }
    }
}