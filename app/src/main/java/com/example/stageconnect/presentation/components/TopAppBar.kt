package com.example.stageconnect.presentation.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.stageconnect.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomTopAppBar(modifier: Modifier = Modifier,
                    title: String = "",
                    navigationIcon: Int = R.drawable.ic_arrow_back,
                    trailingIcon: Int = -1,
                    onTrailingIconClick: () -> Unit = {},
                    onDismiss: () -> Unit,
                    ) {

    TopAppBar(
        title = {
            Text(
                modifier = modifier.padding(start = 5.dp),
                text = title,
                fontWeight = FontWeight.W600,
                fontSize = 24.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        },
        navigationIcon = {
            if (navigationIcon != -1){
                IconButton(onClick = { onDismiss() }) {
                    Icon(
                        painter = painterResource(navigationIcon),
                        contentDescription = "navigationIcon"
                    )
                }
            }
        },
        actions = {
            if (trailingIcon != -1){
                IconButton(onClick = { onTrailingIconClick() }) {
                    Icon(
                        painter = painterResource(trailingIcon),
                        contentDescription = "trailingIcon",
                        tint = Color.Unspecified
                    )
                }
            }
        }
    )
}