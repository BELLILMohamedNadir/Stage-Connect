package com.example.stageconnect.presentation.screens.search.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.stageconnect.R
import com.example.stageconnect.ui.theme.BackgroundGray
import com.example.stageconnect.ui.theme.Blue
import com.example.stageconnect.ui.theme.SoftBlue

@Composable
fun CustomSearchBar(
    modifier: Modifier = Modifier,
    resetText: MutableState<String> = mutableStateOf(""),
    hint: String = stringResource(R.string.search_for_a_job_or_company),
    leadingIcon: Int = R.drawable.ic_search,
    trailingIcon: Int = R.drawable.ic_filter,
    onFilterClick: () -> Unit = {},
    onValueChange: (String) -> Unit
) {
    var text by remember { resetText }
    var isFocused by remember { mutableStateOf(false) }

    TextField(
        value = text,
        onValueChange = {
            text = it
            onValueChange(it)
        },
        modifier = modifier
            .fillMaxWidth()
            .height(54.dp)
            .clip(RoundedCornerShape(15.dp))
            .background(Color.White)
            .border(width = 1.dp, color = if (isFocused) Blue else Color.Transparent, shape = RoundedCornerShape(15.dp))
            .onFocusChanged { focusState ->
                isFocused = focusState.isFocused
            },
        placeholder = {
            Text(text = hint, color = Color.Gray, fontWeight = FontWeight.W500, fontSize = 14.sp)
        },
        leadingIcon = {
            Icon(painter = painterResource(leadingIcon),
                contentDescription = "Search Icon",
                tint = if(isFocused) Blue else Color.Unspecified)
        },
        trailingIcon = {
            if (trailingIcon != -1){
                IconButton(onClick = {onFilterClick()}) {
                    Icon(painter = painterResource(trailingIcon),
                        contentDescription = "filter Icon",
                        tint = Color.Unspecified)
                }
            }
        },
        singleLine = true,
        colors = OutlinedTextFieldDefaults.colors(
            focusedContainerColor = SoftBlue,
            focusedBorderColor = SoftBlue,
            unfocusedBorderColor = Color.Transparent,
            unfocusedContainerColor = BackgroundGray,
            cursorColor = Blue,
        )
    )

}
