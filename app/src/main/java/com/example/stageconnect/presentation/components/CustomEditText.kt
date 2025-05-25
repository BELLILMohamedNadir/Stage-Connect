package com.example.stageconnect.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.stageconnect.data.dtos.EstablishmentsDto
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
    resetText: MutableState<String> = mutableStateOf(""),
    trailingIcon: Int = -1,
    trailingIconWithAction: Boolean = false,
    isEditTextEnabled: Boolean = true,
    leadingIcon: Int = -1,
    isPhoneNumber: Boolean = false,
    isDate: Boolean = false,
    imeAction: ImeAction = ImeAction.Done,
    list: List<String> = emptyList(),
    establishmentList : List<EstablishmentsDto> = emptyList(),
    hasEstablishmentList : Boolean = false,
    keyboardType: KeyboardType = KeyboardType.Text,
    selectedCode: String = "+33",
    onCountryCodeChange: (String) -> Unit = {},
    onTrailingIconClick: (String) -> String = {""},
    onEstablishmentValueChange: (Long) -> Unit = {},
    onValueChange: (String) -> Unit,
) {
    //to control data reset from outside
    resetText.value = defaultText
    var text = resetText.value
    
    var listClicked by remember { mutableStateOf(false) }
    var isDatePickerDialogOpen by remember { mutableStateOf(false) }
    var codeExpanded by remember { mutableStateOf(false) }
    val countryCodes = listOf("+213", "+33", "+1", "+44", "+49", "+212")

    val isEnabled = (list.isEmpty() && !isDate) && !hasEstablishmentList

    Column(horizontalAlignment = Alignment.CenterHorizontally) {

        if (isPhoneNumber) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(15.dp))
                    .background(color = BackgroundGray)
                    .padding(horizontal = 8.dp, vertical = 4.dp)
            ) {
                Box {
                    Text(
                        text = selectedCode,
                        modifier = Modifier
                            .clickable { codeExpanded = true }
                            .padding(horizontal = 2.dp, vertical = 4.dp),
                        color = Color.Black,
                        maxLines = 1
                    )

                    DropdownMenu(
                        expanded = codeExpanded,
                        onDismissRequest = { codeExpanded = false },
                        modifier = Modifier.background(Color.White)
                    ) {
                        countryCodes.forEach { code ->
                            DropdownMenuItem(
                                text = { Text(code) },
                                onClick = {
                                    onCountryCodeChange(code)
                                    codeExpanded = false
                                }
                            )
                        }
                    }
                }

                TextField(
                    value = text,
                    onValueChange = {
                        if (it.length < 12){
                            text = it
                            onValueChange(it)
                        }
                    },
                    placeholder = { Text(text = label, color = GrayFont, maxLines = 1, overflow = TextOverflow.Ellipsis) },
                    modifier = Modifier.weight(1f),
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = BackgroundGray,
                        unfocusedContainerColor = BackgroundGray,
                        disabledContainerColor = BackgroundGray,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        cursorColor = GrayFont,
                        focusedTextColor = Color.Black,
                        unfocusedTextColor = Color.Black,
                    )
                )
            }

        } else {
            TextField(
                value = text,
                onValueChange = {
                    if (isEnabled) {
                        text = it
                        onValueChange(it)
                    }
                },
                placeholder = { Text(text = label, color = GrayFont, maxLines = 1, overflow = TextOverflow.Ellipsis) },
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(15.dp))
                    .background(color = BackgroundGray)
                    .clickable(enabled = !isEnabled && isEditTextEnabled) {
                        if (list.isNotEmpty()) listClicked = !listClicked
                        if (establishmentList.isNotEmpty()) listClicked = !listClicked
                        if (isDate) isDatePickerDialogOpen = !isDatePickerDialogOpen
                    },
                enabled = isEnabled && isEditTextEnabled,
                singleLine = true,
                trailingIcon = {
                    if (list.isNotEmpty()) {
                        Icon(
                            imageVector = if (listClicked) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                            contentDescription = null,
                            tint = GrayFont
                        )
                    } else if (trailingIcon != -1) {
                        if (trailingIconWithAction){
                            IconButton(onClick = {text = onTrailingIconClick(text)}) {
                                Icon(
                                    painter = painterResource(trailingIcon),
                                    contentDescription = "icon"
                                )
                            }
                        }else{
                            Icon(
                                painter = painterResource(trailingIcon),
                                contentDescription = "icon"
                            )
                        }
                    }


                },
                leadingIcon = {
                    if (leadingIcon != -1) {
                        Icon(
                            painter = painterResource(leadingIcon),
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
                    keyboardType = keyboardType,
                    imeAction = imeAction),

            )
        }

        if (listClicked) {
            DropdownMenu(
                expanded = listClicked,
                onDismissRequest = { listClicked = false },
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .background(Color.White)
            ) {
                if (list.isNotEmpty()){
                    list.forEach {
                        DropdownMenuItem(
                            text = { Text(it, color = Color.Black) },
                            onClick = {
                                text = it
                                onValueChange(it)
                                listClicked = false
                            }
                        )
                    }
                }

                if (establishmentList.isNotEmpty()){
                    establishmentList.forEach {
                        DropdownMenuItem(
                            text = { Text(it.name, color = Color.Black) },
                            onClick = {
                                text = it.name
                                onEstablishmentValueChange(it.id)
                                listClicked = false
                            }
                        )
                    }
                }
            }
        }

        if (isDatePickerDialogOpen && isEditTextEnabled) {
            CustomDatePickerDialog(
                onDateSelected = { millis ->
                    val date = millis?.let { Date(it) }
                    val format = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                    text = date?.let { format.format(it) }.orEmpty()
                    onValueChange(text)
                }
            ) {
                isDatePickerDialogOpen = false
            }
        }
    }
}
