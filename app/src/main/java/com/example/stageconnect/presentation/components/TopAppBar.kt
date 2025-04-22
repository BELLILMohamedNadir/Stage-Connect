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
                    leadingIcon: Int = -1,
                    onLeadingIconClick: () -> Unit = {},
                    onDismiss: () -> Unit,
                    ) {
    // to manage leading icon
    var icon by remember{ mutableIntStateOf(leadingIcon) }

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
            IconButton(onClick = { onDismiss() }) {
                Icon(
                   painter = painterResource(navigationIcon),
                    contentDescription = "navigationIcon"
                )
            }
        },
        actions = {
            if (icon != -1){
                    IconButton(onClick = {
                        icon = if (icon == R.drawable.ic_save)
                            R.drawable.ic_save_filled
                        else
                            R.drawable.ic_save

                        onLeadingIconClick() }) {
                        Icon(
                            painter = painterResource(icon),
                            contentDescription = "leadingIcon",
                            tint = Color.Unspecified
                        )
                    }
            }
        }
    )
}