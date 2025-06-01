package com.example.stageconnect.presentation.screens.profile.screens

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.stageconnect.R
import com.example.stageconnect.data.dtos.WorkExperienceDto
import com.example.stageconnect.domain.DateComparator
import com.example.stageconnect.domain.model.enums.EmploymentType
import com.example.stageconnect.domain.model.enums.JobFunction
import com.example.stageconnect.domain.model.enums.JobLevel
import com.example.stageconnect.presentation.components.AppButton
import com.example.stageconnect.presentation.components.CustomEditText
import com.example.stageconnect.presentation.components.CustomTextArea
import com.example.stageconnect.presentation.components.CustomToggleSwitch
import com.example.stageconnect.presentation.components.CustomMessage
import com.example.stageconnect.presentation.components.ObserveResult
import com.example.stageconnect.presentation.screens.profile.viewmodels.WorkExperienceViewModel
import com.example.stageconnect.ui.theme.GrayFont

@Composable
fun WorkExperienceScreen(modifier: Modifier = Modifier,
                         workExperienceViewModel: WorkExperienceViewModel,
                         onNext: () -> Unit
) {
    val inputFields = listOf(
        R.string.job_title to emptyList(),
        R.string.company to emptyList(),
        R.string.from to emptyList(),
        R.string.i_currently_work_here to emptyList(),
        R.string.description_optional to emptyList(),
        R.string.employment_type_ to EmploymentType.entries.map { it.label },
        R.string.location to emptyList(),
        R.string.job_level to JobLevel.entries.map { it.label },
        R.string.job_function to JobFunction.entries.map { it.label }
    )

    val workExperience = workExperienceViewModel.getWorkExperience()
    val jobTitle = rememberSaveable { mutableStateOf(workExperience?.jobTitle ?: "") }
    val company = rememberSaveable { mutableStateOf(workExperience?.company ?: "") }
    val description = rememberSaveable { mutableStateOf(workExperience?.description ?: "") }
    val startDate = rememberSaveable { mutableStateOf(workExperience?.startDate ?: "") }
    val endDate = rememberSaveable { mutableStateOf(workExperience?.endDate ?: "") }
    val currentlyWorkHere = rememberSaveable { mutableStateOf(workExperience?.currentWorkHere ?: false) }
    val employmentType = rememberSaveable { mutableStateOf(workExperience?.employmentType ?: "") }
    val location = rememberSaveable { mutableStateOf(workExperience?.location ?: "") }
    val jobLevel = rememberSaveable { mutableStateOf(workExperience?.jobLevel ?: "") }
    val jobFunction = rememberSaveable { mutableStateOf(workExperience?.jobFunction ?: "") }
    val isLoading = rememberSaveable { mutableStateOf(false) }
    var showErrorMessage by rememberSaveable { mutableStateOf(false) }

    val stateMap = mapOf(
        R.string.job_title to jobTitle,
        R.string.employment_type_ to employmentType,
        R.string.location to location,
        R.string.job_level to jobLevel,
        R.string.job_function to jobFunction,
    )

    val createWorkExperienceResult by workExperienceViewModel.createWorkExperienceResult.observeAsState()
    val updateWorkExperienceResult by workExperienceViewModel.updateWorkExperienceResult.observeAsState()
    val deleteWorkExperienceResult by workExperienceViewModel.deleteWorkExperienceResult.observeAsState()
    val deleteWorkExperience by workExperienceViewModel.deleteWorkExperience.observeAsState()

    LaunchedEffect(deleteWorkExperience) {
        if (deleteWorkExperience == true && workExperience?.id != null) {
            workExperienceViewModel.deleteWorkExperience(workExperience.id!!)
        }
    }

    ObserveResult(
        result = deleteWorkExperienceResult,
        onLoading = {isLoading.value = true},
        onSuccess = {
            isLoading.value = false
            onNext()
        },
        onError = {
            isLoading.value = false
            CustomMessage.Show(stringResource(R.string.error_occurred))
        }
    )

    ObserveResult(
        result = createWorkExperienceResult,
        onLoading = {isLoading.value = true},
        onSuccess = {
            isLoading.value = false
            onNext()
        },
        onError = {
            isLoading.value = false
            CustomMessage.Show(stringResource(R.string.error_occurred))
        }
    )

    ObserveResult(
        result = updateWorkExperienceResult,
        onLoading = {isLoading.value = true},
        onSuccess = {
            isLoading.value = false
            onNext()
        },
        onError = {
            isLoading.value = false
            CustomMessage.Show(stringResource(R.string.error_occurred))
        }
    )

    DisposableEffect(Unit) {
        onDispose {
            workExperienceViewModel.setWorkExperience(null)
        }
    }


    LazyColumn(modifier = modifier.fillMaxSize()
    ){

        item {
            Column(
                modifier = Modifier.fillMaxSize().padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)) {

                inputFields.forEach { (label, list) ->
                    when (label) {
                        R.string.description_optional -> {
                            Column (
                                modifier = Modifier.fillMaxWidth().padding(horizontal = 10.dp),
                                horizontalAlignment = Alignment.Start
                            ){
                                Text(text = stringResource(R.string.description_optional), color = GrayFont, fontSize = 14.sp)
                                Spacer(modifier = Modifier.height(10.dp))
                                CustomTextArea(
                                    defaultText = description.value,
                                    label = stringResource(R.string.description),
                                    ) {
                                    description.value = it
                                }
                            }
                        }

                        R.string.from -> {
                            Row(modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween) {
                                Column(
                                    modifier = Modifier.weight(1f).padding(horizontal = 10.dp)
                                ) {
                                    Text(text = stringResource(R.string.from), color = GrayFont, fontSize = 14.sp,
                                        modifier = Modifier.padding(start = 10.dp))
                                    Spacer(modifier = Modifier.height(4.dp))
                                    CustomEditText(
                                        defaultText = startDate.value,
                                        label = stringResource(R.string.from),
                                        isDate = true,
                                        trailingIcon = R.drawable.ic_polygon,
                                        onValueChange = { startDate.value = it }
                                    )
                                }
                                if (!currentlyWorkHere.value){
                                    Spacer(modifier = Modifier.width(10.dp))
                                    Column (
                                        modifier = Modifier.weight(1f).padding(horizontal = 10.dp)
                                    ){
                                        Text(text = stringResource(R.string.to), color = GrayFont, fontSize = 14.sp,
                                            modifier = Modifier.padding(start = 10.dp))
                                        Spacer(modifier = Modifier.height(4.dp))
                                        CustomEditText(
                                            defaultText = endDate.value,
                                            label = stringResource(R.string.to),
                                            isDate = true,
                                            isEditTextEnabled = !currentlyWorkHere.value,
                                            trailingIcon = R.drawable.ic_polygon,
                                            onValueChange = { endDate.value = it }
                                        )
                                    }
                                }

                            }
                        }

                        R.string.i_currently_work_here -> {
                            Column (
                                modifier = Modifier.fillMaxWidth().padding(horizontal = 10.dp),
                                horizontalAlignment = Alignment.Start
                            ){
                                CustomToggleSwitch(label = stringResource(R.string.i_currently_work_here),
                                    isChecked = currentlyWorkHere.value){
                                    currentlyWorkHere.value = it
                                }
                            }
                        }

                        R.string.job_title -> {
                            Column (
                                modifier = Modifier.fillMaxWidth().padding(horizontal = 10.dp),
                                horizontalAlignment = Alignment.Start
                            ){
                                Text(text = stringResource(label), color = GrayFont, fontSize = 14.sp,
                                    modifier = Modifier.fillMaxWidth().padding(start = 16.dp))
                                Spacer(modifier = Modifier.height(10.dp))
                                CustomEditText(
                                    defaultText = jobTitle.value,
                                    label = stringResource(label),
                                    keyboardType = KeyboardType.Text,
                                    onValueChange = { jobTitle.value = it}
                                )
                            }
                        }

                        R.string.company -> {
                            Column (
                                modifier = Modifier.fillMaxWidth().padding(horizontal = 10.dp),
                                horizontalAlignment = Alignment.Start
                            ){
                                Text(text = stringResource(label), color = GrayFont, fontSize = 14.sp,
                                    modifier = Modifier.fillMaxWidth().padding(start = 16.dp))
                                Spacer(modifier = Modifier.height(10.dp))
                                CustomEditText(
                                    defaultText = company.value,
                                    label = stringResource(label),
                                    keyboardType = KeyboardType.Text,
                                    onValueChange = { company.value = it }
                                )
                            }
                        }


                        else -> {
                            Column (
                                modifier = Modifier.fillMaxWidth().padding(horizontal = 10.dp),
                                horizontalAlignment = Alignment.Start
                            ){
                                Text(text = stringResource(label), color = GrayFont, fontSize = 14.sp,
                                    modifier = Modifier.fillMaxWidth().padding(start = 16.dp))
                                Spacer(modifier = Modifier.height(10.dp))
                                CustomEditText(
                                    defaultText = stateMap[label]?.value.toString(),
                                    label = stringResource(label),
                                    list = list,
                                    keyboardType = KeyboardType.Text,
                                    onValueChange = { stateMap[label]?.value = it }
                                )
                            }
                        }
                    }
                }
                AppButton(text = if (workExperience != null) stringResource(R.string.update) else stringResource(R.string.save),
                    isLoading = isLoading) {
                    val workExperienceDto =  WorkExperienceDto(
                        id = workExperience?.id,
                        jobTitle = jobTitle.value.trim(),
                        company = company.value.trim(),
                        startDate = startDate.value.trim(),
                        endDate = endDate.value.trim(),
                        currentWorkHere = currentlyWorkHere.value,
                        description = description.value.trim(),
                        employmentType = employmentType.value.trim(),
                        location = location.value.trim(),
                        jobLevel = jobLevel.value.trim(),
                        jobFunction = jobFunction.value.trim(),
                        userId = -1
                    )
                    if (isValid(workExperienceDto)){
                        if (workExperience != null){
                            workExperienceViewModel.updateWorkExperience(workExperienceDto)
                        }else{
                            workExperienceViewModel.createWorkExperience(workExperienceDto)
                        }
                    }
                    else
                        showErrorMessage = true
                }
            }
        }
    }
    if (showErrorMessage){
        showErrorMessage = false
        CustomMessage.Show(stringResource(R.string.add_necessary_data))
    }
}

fun isValid(workExperienceDto: WorkExperienceDto): Boolean {
    var valid =  workExperienceDto.jobTitle.isNotBlank() &&
            workExperienceDto.company.isNotBlank() &&
            workExperienceDto.startDate.isNotBlank() &&
            workExperienceDto.employmentType.isNotBlank() &&
            workExperienceDto.location.isNotBlank() &&
            workExperienceDto.jobLevel.isNotBlank() &&
            workExperienceDto.jobFunction.isNotBlank()

    if (valid && !workExperienceDto.currentWorkHere)  valid = workExperienceDto.endDate.isNotBlank()

    val dateComparator = DateComparator(dateFormat = "yyyy-MM-dd")

    if (valid && !workExperienceDto.currentWorkHere){
        valid = dateComparator.isBefore(workExperienceDto.startDate, workExperienceDto.endDate?:"")
    }
    return valid && !dateComparator.isAfterCurrentDate(workExperienceDto.startDate)
}