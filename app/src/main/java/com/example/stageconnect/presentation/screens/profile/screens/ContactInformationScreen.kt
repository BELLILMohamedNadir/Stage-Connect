package com.example.stageconnect.presentation.screens.profile.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.stageconnect.R
import com.example.stageconnect.data.dtos.StudentDto
import com.example.stageconnect.presentation.components.AppButton
import com.example.stageconnect.presentation.components.CustomEditText
import com.example.stageconnect.presentation.components.CustomMessage
import com.example.stageconnect.presentation.components.NotFound
import com.example.stageconnect.presentation.components.ObserveResult
import com.example.stageconnect.presentation.screens.profile.viewmodels.ProfileViewModel

@SuppressLint("UnrememberedMutableState")
@Composable
fun ContactInformationScreen(modifier: Modifier = Modifier,
                             viewModel: ProfileViewModel,
                             onNext: () -> Unit) {
    val user = viewModel.user.value

    if (user != null) {
        var selectedCountryCode by remember { mutableStateOf("+33") }

        val inputFields = listOf(
            stringResource(R.string.address) to R.drawable.ic_position,
            stringResource(R.string.phone_number) to R.drawable.ic_phone,
            stringResource(R.string.email) to R.drawable.ic_email_
        )

        val address = rememberSaveable { mutableStateOf(user.address ?: "") }
        val phoneNumber = rememberSaveable { mutableStateOf(user.phone ?: "") }
        val email = rememberSaveable { mutableStateOf(user.email ?: "") }
        val isLoading = rememberSaveable { mutableStateOf(false) }
        var showErrorMessage by rememberSaveable { mutableStateOf(false) }

        val updateStudentResult by viewModel.updateStudentResult.observeAsState()

        ObserveResult(
            result = updateStudentResult,
            onLoading = {isLoading.value = true},
            onSuccess = {
                isLoading.value = false
                viewModel.clearData()
                onNext()
            },
            onError = {
                isLoading.value = false
                CustomMessage.Show(stringResource(R.string.error_occurred))
            }
        )

        Column(
            modifier = modifier.fillMaxSize().padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                modifier = Modifier.fillMaxWidth().fillMaxHeight(fraction = 0.45f).padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceBetween
            ) {

                inputFields.forEach { (label, icon) ->
                    when (label) {
                        stringResource(R.string.phone_number) -> {
                            CustomEditText(
                                label = label,
                                defaultText = phoneNumber.value,
                                isPhoneNumber = true,
                                keyboardType = KeyboardType.Number,
                                onValueChange = { phoneNumber.value = it },
                                selectedCode = selectedCountryCode,
                                onCountryCodeChange = {
                                    selectedCountryCode = it
                                }
                            )
                        }

                        stringResource(R.string.email) -> {
                            CustomEditText(
                                label = label,
                                defaultText = email.value,
                                leadingIcon = icon,
                                keyboardType = KeyboardType.Email,
                                onValueChange = { email.value = it }
                            )
                        }

                        else -> {
                            CustomEditText(
                                label = label,
                                defaultText = address.value,
                                leadingIcon = icon,
                                keyboardType = KeyboardType.Text,
                                onValueChange = { address.value = it }
                            )
                        }
                    }
                }
            }
            AppButton(text = stringResource(R.string.save), isLoading = isLoading) {
                val studentDto = StudentDto(
                    address = address.value,
                    email = email.value,
                    phone = phoneNumber.value
                )
                if (isValid_(studentDto)) viewModel.updateStudent(studentDto) else showErrorMessage = true
            }
        }
        if (showErrorMessage){
            showErrorMessage = false
            CustomMessage.Show(stringResource(R.string.add_necessary_data))
        }
    } else {
        NotFound(showMessage = false)
    }
}
fun isValid_(studentDto: StudentDto): Boolean {
    return studentDto.address!!.isNotBlank() &&
            studentDto.email!!.isNotBlank() &&
            studentDto.phone!!.isNotBlank()
}
