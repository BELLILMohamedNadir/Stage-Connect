package com.example.stageconnect.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.stageconnect.R
import com.example.stageconnect.presentation.screens.signup.components.CustomDatePickerDialog
import com.example.stageconnect.ui.theme.BackgroundGray
import com.example.stageconnect.ui.theme.GrayFont
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun CustomEditText(
    modifier: Modifier = Modifier,
    label: String = "",
    defaultText: String = "",
    icon: Int = -1,
    isDate: Boolean = false,
    list: List<String> = emptyList(),
    keyboardType: KeyboardType = KeyboardType.Text,
    onValueChange: (String) -> Unit
) {
    var text by remember { mutableStateOf(defaultText)}
    var listClicked by remember { mutableStateOf(false) }
    var isDatePickerDialogOpen by remember { mutableStateOf(false) }
    val isEnabled = list.isEmpty() && !isDate

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        TextField(
            value = text,
            onValueChange = {
                if (isEnabled) {
                    text = it
                    onValueChange(it)
                }
            },
            label = { Text(text = label, color = GrayFont) },
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(15.dp))
                .background(color = BackgroundGray)
                .clickable(enabled = !isEnabled) {
                    if (list.isNotEmpty()) listClicked = !listClicked
                    if (isDate) isDatePickerDialogOpen = !isDatePickerDialogOpen
                },
            enabled = isEnabled,
            singleLine = true,
            trailingIcon = {
                if (list.isNotEmpty()) {
                    Icon(
                        imageVector = if (listClicked) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                        contentDescription = null,
                        tint = GrayFont
                    )
                } else if (icon != -1) {
                    Icon(
                        painter = painterResource(icon),
                        contentDescription = "icon"
                    )
                }
            },
            colors = TextFieldDefaults.colors(
                focusedContainerColor = BackgroundGray,
                unfocusedContainerColor = BackgroundGray,
                disabledContainerColor = BackgroundGray,
                disabledTextColor = Color.Black,
                disabledTrailingIconColor = Color.Unspecified,
                disabledLeadingIconColor = Color.Unspecified,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
                cursorColor = GrayFont,
                focusedTextColor = Color.Black,
                unfocusedTextColor = Color.Black,
            ),
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = keyboardType
            )
        )

        // Dropdown List
        DropdownMenu(
            expanded = listClicked,
            onDismissRequest = { listClicked = false },
            modifier = Modifier
                .fillMaxWidth(fraction = 0.9f)
                .background(Color.White)
        ) {

            list.forEach {
                DropdownMenuItem(
                    text = { Text(text = it, color = Color.Black) },
                    onClick = {
                        text = it
                        onValueChange(it)
                        listClicked = false
                    }
                )
            }

        }

        // Date Picker
        if (isDatePickerDialogOpen){
            CustomDatePickerDialog(
                onDateSelected = { miles ->
                    val date = miles?.let { Date(it) }
                    val format = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

                    text = date?.let { format.format(it) }.toString()

                }
            ) {
                isDatePickerDialogOpen = false
            }
        }
    }
}
