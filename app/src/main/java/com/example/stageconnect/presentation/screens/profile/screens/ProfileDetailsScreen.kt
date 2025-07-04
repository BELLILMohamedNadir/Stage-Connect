package com.example.stageconnect.presentation.screens.profile.screens

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.stageconnect.R
import com.example.stageconnect.data.dtos.StudentDto
import com.example.stageconnect.presentation.components.AppButton
import com.example.stageconnect.presentation.components.CustomEditText
import com.example.stageconnect.presentation.components.CustomMessage
import com.example.stageconnect.presentation.components.NotFound
import com.example.stageconnect.presentation.components.ObserveResult
import com.example.stageconnect.presentation.components.ProfileImage
import com.example.stageconnect.presentation.screens.profile.viewmodels.ProfileViewModel
import com.example.stageconnect.ui.theme.GrayFont

@Composable
fun ProfileDetailsScreen(modifier: Modifier = Modifier,
                         viewModel: ProfileViewModel = hiltViewModel(),
                         onNext: () -> Unit) {

    val user = viewModel.user.value

    if (user != null){
        val inputFields = listOf(
            stringResource(R.string.first_name) to -1,
            stringResource(R.string.middle_or_last_name) to -1,
            stringResource(R.string.date_of_birth) to R.drawable.ic_calender,
            stringResource(R.string.current_position) to R.drawable.ic_work_experience
        )

        val imageUri by viewModel.fileUri
        val initialPhotoUri = imageUri

        val firstName = rememberSaveable { mutableStateOf(user.firstName ?: "") }
        val lastName = rememberSaveable { mutableStateOf(user.name  ?: "") }
        val dateOfBirth = rememberSaveable { mutableStateOf(user.dateOfBirth  ?: "") }
        val currentPosition = rememberSaveable { mutableStateOf(user.currentPosition  ?: "") }
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

        val launcher = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.GetContent()
        ) { uri ->
            uri?.let { viewModel.onFileSelected(it) }
        }

        Column(
            modifier = modifier.fillMaxSize().padding(vertical = 16.dp, horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ){
            Box(
                modifier = Modifier.width(130.dp),
                contentAlignment = Alignment.Center
            ){
                ProfileImage((imageUri ?: user.photo).toString(), 120.dp)
                IconButton(modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(top = 16.dp, start = 16.dp), onClick = {launcher.launch("image/*")}) {
                    Icon(painter = painterResource(R.drawable.ic_add)
                        , contentDescription = "ic_add",
                        tint = Color.Unspecified)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
            inputFields.forEach { (label, icon) ->
                when(label){
                    stringResource(R.string.date_of_birth) -> {
                        Column (
                            modifier = Modifier.fillMaxWidth().padding(horizontal = 10.dp),
                            horizontalAlignment = Alignment.Start
                        ){
                            Text(text = label, color = GrayFont, fontSize = 14.sp,
                                modifier = Modifier.fillMaxWidth().padding(start = 16.dp))
                            Spacer(modifier = Modifier.height(10.dp))
                            CustomEditText(defaultText = dateOfBirth.value, label = label, trailingIcon = icon, isDate = true, onValueChange = {
                                dateOfBirth.value = it
                            })
                        }

                    }
                    stringResource(R.string.first_name) -> {
                        Column (
                            modifier = Modifier.fillMaxWidth().padding(horizontal = 10.dp),
                            horizontalAlignment = Alignment.Start
                        ){
                            Text(text = label, color = GrayFont, fontSize = 14.sp,
                                modifier = Modifier.fillMaxWidth().padding(start = 16.dp))
                            Spacer(modifier = Modifier.height(10.dp))
                            CustomEditText(defaultText = firstName.value ,label = label, trailingIcon = icon, keyboardType = KeyboardType.Email) {
                                firstName.value = it
                            }
                        }
                    }
                    stringResource(R.string.middle_or_last_name) -> {
                        Column (
                            modifier = Modifier.fillMaxWidth().padding(horizontal = 10.dp),
                            horizontalAlignment = Alignment.Start
                        ){
                            Text(text = label, color = GrayFont, fontSize = 14.sp,
                                modifier = Modifier.fillMaxWidth().padding(start = 16.dp))
                            Spacer(modifier = Modifier.height(10.dp))
                            CustomEditText(defaultText = lastName.value ,label = label, trailingIcon = icon, keyboardType = KeyboardType.Email) {
                                lastName.value = it
                            }
                        }
                    }
                    stringResource(R.string.current_position) -> {
                        Column (
                            modifier = Modifier.fillMaxWidth().padding(horizontal = 10.dp),
                            horizontalAlignment = Alignment.Start
                        ){
                            Text(text = label, color = GrayFont, fontSize = 14.sp,
                                modifier = Modifier.fillMaxWidth().padding(start = 16.dp))
                            Spacer(modifier = Modifier.height(10.dp))
                            CustomEditText(defaultText = currentPosition.value ,label = label, trailingIcon = icon, keyboardType = KeyboardType.Email) {
                                currentPosition.value = it
                            }
                        }

                    }
                    else -> {
                        Column (
                            modifier = Modifier.fillMaxWidth().padding(horizontal = 10.dp),
                            horizontalAlignment = Alignment.Start
                        ){
                            Text(text = label, color = GrayFont, fontSize = 14.sp,
                                modifier = Modifier.fillMaxWidth().padding(start = 16.dp))
                            Spacer(modifier = Modifier.height(10.dp))
                            CustomEditText(label = label, trailingIcon = icon) { }
                        }
                    }
                }
            }

            AppButton(text = stringResource(R.string.save), isLoading = isLoading) {
                val studentDto = StudentDto(
                    name = lastName.value,
                    firstName = firstName.value,
                    dateOfBirth = dateOfBirth.value,
                    currentPosition = currentPosition.value,
                )
                if (isValid(studentDto)){
                    viewModel.updateStudent(studentDto, imageUri)
                }else{
                    showErrorMessage = true
                }
            }
        }
        if (showErrorMessage){
            showErrorMessage = false
            CustomMessage.Show(stringResource(R.string.add_necessary_data))
        }
    }else{
        NotFound(showMessage = false)
    }
}
fun isValid(studentDto: StudentDto): Boolean {
    return studentDto.name!!.isNotBlank() &&
            studentDto.firstName!!.isNotBlank() &&
            studentDto.dateOfBirth!!.isNotBlank() &&
            studentDto.currentPosition!!.isNotBlank()
}