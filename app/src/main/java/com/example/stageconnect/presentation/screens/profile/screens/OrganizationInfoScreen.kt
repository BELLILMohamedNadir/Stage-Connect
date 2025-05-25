package com.example.stageconnect.presentation.screens.profile.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.stageconnect.R
import com.example.stageconnect.data.dtos.RecruiterDto
import com.example.stageconnect.presentation.components.AppButton
import com.example.stageconnect.presentation.components.CustomEditText
import com.example.stageconnect.presentation.components.CustomTextArea
import com.example.stageconnect.presentation.components.ErrorMessage
import com.example.stageconnect.presentation.components.NotFound
import com.example.stageconnect.presentation.components.ObserveResult
import com.example.stageconnect.presentation.screens.profile.viewmodels.ProfileViewModel

@SuppressLint("UnrememberedMutableState")
@Composable
fun OrganizationInfoScreen(modifier: Modifier = Modifier,
                             viewModel: ProfileViewModel) {
    val user = viewModel.user.value

    if (user != null) {
        val inputFields = listOf(
            stringResource(R.string.name),
            stringResource(R.string.address),
            stringResource(R.string.description)
        )

        val organizationName = rememberSaveable { mutableStateOf(user.organizationName ?: "") }
        val address = rememberSaveable { mutableStateOf(user.address ?: "") }
        val description = rememberSaveable { mutableStateOf(user.summary ?: "") }
        val isLoading = rememberSaveable { mutableStateOf(false) }
        var showErrorMessage by rememberSaveable { mutableStateOf(false) }

        val updateRecruiterResult by viewModel.updateRecruiterResult.observeAsState()

        ObserveResult(
            result = updateRecruiterResult,
            onLoading = {isLoading.value = true},
            onSuccess = {
                isLoading.value = false
                viewModel.clearData()
            },
            onError = {
                isLoading.value = false
                ErrorMessage.Show(stringResource(R.string.error_occurred))
            }
        )

        Column(
            modifier = modifier.fillMaxSize().padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                modifier = Modifier.fillMaxWidth().weight(1f).padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {

                inputFields.forEach { label ->
                    when (label) {
                        stringResource(R.string.description) -> {
                            Column (
                                horizontalAlignment = Alignment.CenterHorizontally
                            ){
                                Text(
                                    text = label, color = Color.Black, fontSize = 14.sp,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(start = 8.dp)
                                )
                                Spacer(modifier = Modifier.height(10.dp))
                                CustomTextArea(
                                    defaultText = description.value
                                ) { description.value = it }
                            }
                        }

                        stringResource(R.string.name) -> {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(
                                    text = label, color = Color.Black, fontSize = 14.sp,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(start = 8.dp)
                                )
                                Spacer(modifier = Modifier.height(10.dp))
                                CustomEditText(
                                    label = label,
                                    defaultText = organizationName.value,
                                    keyboardType = KeyboardType.Text,
                                    onValueChange = { organizationName.value = it }
                                )
                            }
                        }
                        else -> {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(
                                    text = label, color = Color.Black, fontSize = 14.sp,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(start = 8.dp)
                                )
                                Spacer(modifier = Modifier.height(10.dp))
                                CustomEditText(
                                    label = label,
                                    defaultText = address.value,
                                    keyboardType = KeyboardType.Text,
                                    onValueChange = { address.value = it }
                                )
                            }
                        }
                    }
                }
            }
            AppButton(modifier = Modifier.fillMaxWidth(fraction = 0.8f).padding(vertical = 8.dp), text = stringResource(R.string.save), isLoading = isLoading) {
                val recruiterDto = RecruiterDto(
                    address = address.value,
                    organizationName = organizationName.value,
                    summary = description.value
                )
                if (isValid_(recruiterDto)) viewModel.updateRecruiter(recruiterDto) else showErrorMessage = true
            }
        }
        if (showErrorMessage){
            showErrorMessage = false
            ErrorMessage.Show(stringResource(R.string.add_necessary_data))
        }
    } else {
        NotFound(showMessage = false)
    }
}
fun isValid_(recruiterDto: RecruiterDto): Boolean {
    return recruiterDto.address!!.isNotBlank() &&
            recruiterDto.organizationName!!.isNotBlank() &&
            recruiterDto.summary!!.isNotBlank()
}
