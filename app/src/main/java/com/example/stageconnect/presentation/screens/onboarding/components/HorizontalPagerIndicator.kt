package com.example.stageconnect.presentation.screens.onboarding.components

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.background
import androidx.compose.ui.Alignment
import com.example.stageconnect.ui.theme.Blue

@Composable
fun HorizontalPagerIndicator(
    pageCount: Int,
    currentPage: Int,
    modifier: Modifier = Modifier,
    activeColor: Color = Blue,
    inactiveColor: Color = Color.Gray,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 16.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        repeat(pageCount) { index ->
            val isSelected = index == currentPage
            Dot(
                isSelected = isSelected,
                activeColor = activeColor,
                inactiveColor = inactiveColor
            )
            if (index != pageCount - 1) {
                Spacer(modifier = Modifier.width(8.dp))
            }
        }
    }
}

@Composable
fun Dot(
    isSelected: Boolean,
    activeColor: Color,
    inactiveColor: Color
) {
    val targetSize = if (isSelected) 12.dp else 8.dp
    val animatedSize = animateDpAsState(targetValue = targetSize, label = "")

    val color = if (isSelected) activeColor else inactiveColor

    Box(
        modifier = Modifier
            .size(animatedSize.value)
            .clip(CircleShape)
            .background(color)
    )
}
