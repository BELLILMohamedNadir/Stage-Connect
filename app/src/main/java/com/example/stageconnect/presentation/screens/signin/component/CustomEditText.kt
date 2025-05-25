package com.example.stageconnect.presentation.screens.signin.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.stageconnect.R

@Composable
fun CustomEditText(
    modifier: Modifier = Modifier,
    text: MutableState<String>,
    isPassword: Boolean = false,
    leadingIcon: Int,
    trailingIcon: Int = R.drawable.ic_open_eye,
    colorTint: Color = Color.Black,
    label: String = "",
    hint: String
) {

    var isPasswordVisible by remember { mutableStateOf(false) }

    TextField(
        value = text.value,
        onValueChange = { text.value = it },
//        label = {
//            Text(text = label, color = Color.Black)
//        },
        placeholder = {
            Text(text = label, color = Color.Gray)
        },
        textStyle = TextStyle(fontSize = 16.sp),
        singleLine = true,
        visualTransformation = if (isPassword){
            if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation()
        }else VisualTransformation.None
            ,
        leadingIcon = {
            Icon(
                painter = painterResource(leadingIcon),
                contentDescription = "Icon",
                tint = colorTint
            )
        },
        trailingIcon = {
            if (isPassword) {
                val visibilityIcon = if (isPasswordVisible) painterResource(R.drawable.ic_closed_eye) else painterResource(R.drawable.ic_open_eye)
                val description = if (isPasswordVisible) "Hide password" else "Show password"

                IconButton(onClick = { isPasswordVisible = !isPasswordVisible }
                ) {
                    Icon(
                        painter = visibilityIcon,
                        contentDescription = description,
                        tint = Color.Black
                    )
                }
            }
        },
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = Color.Black,
            unfocusedBorderColor = Color.LightGray,
            cursorColor = Color.Black
        ),
        modifier = modifier
            .fillMaxWidth()
    )
}