package com.example.stageconnect.presentation.screens.profile.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.stageconnect.R
import com.example.stageconnect.data.dtos.StudentDto
import com.example.stageconnect.presentation.components.AppButton
import com.example.stageconnect.presentation.components.CustomTextArea
import com.example.stageconnect.presentation.components.ErrorMessage
import com.example.stageconnect.presentation.components.ObserveResult
import com.example.stageconnect.presentation.screens.profile.viewmodels.ProfileViewModel
import com.example.stageconnect.ui.theme.GrayFont

@Composable
fun SummaryScreen(modifier: Modifier = Modifier,
                  profileViewModel: ProfileViewModel,
                  onNext: () -> Unit) {

    val summary = remember { mutableStateOf("") }
    val isLoading = remember { mutableStateOf(false) }
    var showErrorMessage by remember { mutableStateOf(false) }


    val updateStudentResult by profileViewModel.updateStudentResult.observeAsState()

    ObserveResult(
        result = updateStudentResult,
        onLoading = {isLoading.value = true},
        onSuccess = {
            isLoading.value = false
            profileViewModel.clearData()
            onNext()
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
            modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp).fillMaxHeight(fraction = 0.7f),
            horizontalAlignment = Alignment.Start
        ) {
            Text(text = stringResource(R.string.summary_max_500_characters),
                color = GrayFont,
                fontSize = 14.sp)
            Spacer(modifier = Modifier.height(10.dp))
            CustomTextArea(
                fraction = 0.95f,
                label = stringResource(R.string.summary),
                maxChars = 500
            ) {
                summary.value = it
            }
        }
        AppButton(text = stringResource(R.string.save),
            isLoading = isLoading) {
            if (summary.value.isNotBlank()){
                val studentDto = StudentDto(
                    summary = summary.value
                )
                profileViewModel.updateStudent(studentDto)
            }else{
                showErrorMessage = true
            }
        }
    }
    if (showErrorMessage){
        showErrorMessage = false
        ErrorMessage.Show(stringResource(R.string.add_necessary_data))
    }
}