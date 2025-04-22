package com.example.stageconnect.presentation.screens.signup.screens

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import com.example.stageconnect.R
import com.example.stageconnect.presentation.components.AppButton
import com.example.stageconnect.presentation.components.CustomEditText
import com.example.stageconnect.presentation.screens.signup.viewmodels.UserInformationViewModel

@Composable
fun UserInformationScreen(modifier: Modifier = Modifier,
                          viewModel: UserInformationViewModel = hiltViewModel(),
                          onNext: () -> Unit) {
    val inputFields = listOf(
        stringResource(R.string.first_name) to -1,
        stringResource(R.string.middle_or_last_name) to -1,
        stringResource(R.string.date_of_birth) to R.drawable.ic_calender,
        stringResource(R.string.email) to R.drawable.ic_email_,
        stringResource(R.string.phone_number) to R.drawable.ic_phone,
        stringResource(R.string.gender) to R.drawable.ic_polygon
    )

    val imageUri by viewModel.imageUri

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri ->
        uri?.let { viewModel.onImageSelected(it) }
    }

    Column(
        modifier = modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ){
        Box(
            modifier = Modifier.width(130.dp),
            contentAlignment = Alignment.Center
        ){
            Image(painter =
                if (imageUri != null)   rememberAsyncImagePainter(imageUri) else painterResource(R.drawable.ic_profile)
                , contentDescription = "ic_profile",
                modifier = Modifier.size(120.dp)
                    .clip(shape = CircleShape),
                contentScale = ContentScale.Crop)
            IconButton(modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(top = 16.dp, start = 16.dp), onClick = {launcher.launch("image/*")}) {
                Icon(painter = painterResource(R.drawable.ic_add)
                    , contentDescription = "ic_add",
                    tint = Color.Unspecified)
            }
        }

        inputFields.forEach { (label, icon) ->
            when(label){
                stringResource(R.string.gender) -> {
                    CustomEditText(label = label, icon = icon,
                        list = listOf(stringResource(R.string.male), stringResource(R.string.female)),
                        onValueChange = {})
                }
                stringResource(R.string.date_of_birth) -> {
                    CustomEditText(label = label, icon = icon, isDate = true, onValueChange = {})
                }
                stringResource(R.string.phone_number) -> {
                    CustomEditText(label = label, icon = icon, keyboardType = KeyboardType.Number) { }
                }
                stringResource(R.string.email) -> {
                    CustomEditText(label = label, icon = icon, keyboardType = KeyboardType.Email) { }
                }
                else -> {
                    CustomEditText(label = label, icon = icon) { }
                }
            }
        }

        AppButton(text = stringResource(R.string.continue_)) {
            onNext()
        }
    }
}