package com.example.stageconnect.presentation.screens.profile.screens

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
import com.example.stageconnect.data.dtos.EducationDto
import com.example.stageconnect.domain.DateComparator
import com.example.stageconnect.domain.model.enums.Education
import com.example.stageconnect.presentation.components.AppButton
import com.example.stageconnect.presentation.components.CustomEditText
import com.example.stageconnect.presentation.components.CustomTextArea
import com.example.stageconnect.presentation.components.CustomToggleSwitch
import com.example.stageconnect.presentation.components.CustomMessage
import com.example.stageconnect.presentation.components.ObserveResult
import com.example.stageconnect.presentation.screens.profile.viewmodels.EducationViewModel
import com.example.stageconnect.ui.theme.GrayFont

@Composable
fun EducationScreen(modifier: Modifier = Modifier,
                    educationViewModel: EducationViewModel = hiltViewModel(),
                    onNext: () -> Unit
) {

    val inputFields = listOf(
        R.string.education_ to Education.entries.map { it.label },
        R.string.course to emptyList(),
        R.string.university to emptyList(),
        R.string.from to emptyList(),
        R.string.graduated to emptyList(),
        R.string.gpa to emptyList(),
        R.string.description_optional to emptyList(),
    )

    val education = rememberSaveable { mutableStateOf("") }
    val course = rememberSaveable { mutableStateOf("") }
    val university = rememberSaveable { mutableStateOf("") }
    val startDate = rememberSaveable { mutableStateOf("") }
    val endDate = rememberSaveable { mutableStateOf("") }
    val isGraduated = rememberSaveable { mutableStateOf(false) }
    val gpa = rememberSaveable { mutableStateOf("") }
    val total = rememberSaveable { mutableStateOf("") }
    val description = rememberSaveable { mutableStateOf("") }
    val isLoading = rememberSaveable { mutableStateOf(false) }
    var showErrorMessage by rememberSaveable { mutableStateOf(false) }

    val stateMap = mapOf(
        R.string.course to course,
        R.string.university to university,
    )

    val createEducationResult by educationViewModel.createEducationResult.observeAsState()

    ObserveResult(
        result = createEducationResult,
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

    LazyColumn(modifier = modifier.fillMaxSize()){
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
                                    label = stringResource(R.string.description),

                                    ) {
                                    description.value = it
                                }
                            }
                        }

                        R.string.education_ ->{
                            Column (
                                modifier = Modifier.fillMaxWidth().padding(horizontal = 10.dp),
                                horizontalAlignment = Alignment.Start
                            ){
                                Text(text = stringResource(label), color = GrayFont, fontSize = 14.sp,
                                    modifier = Modifier.fillMaxWidth().padding(start = 16.dp))
                                Spacer(modifier = Modifier.height(10.dp))
                                CustomEditText(
                                    label = stringResource(label),
                                    list = list,
                                    keyboardType = KeyboardType.Text,
                                    onValueChange = { education.value = it }
                                )
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
                                        label = stringResource(R.string.from),
                                        isDate = true,
                                        trailingIcon = R.drawable.ic_polygon,
                                        onValueChange = { startDate.value = it }
                                    )
                                }
                                if (isGraduated.value){
                                    Spacer(modifier = Modifier.width(10.dp))
                                    Column (
                                        modifier = Modifier.weight(1f).padding(horizontal = 10.dp)
                                    ){
                                        Text(text = stringResource(R.string.to), color = GrayFont, fontSize = 14.sp,
                                            modifier = Modifier.padding(start = 10.dp))
                                        Spacer(modifier = Modifier.height(4.dp))
                                        CustomEditText(
                                            label = stringResource(R.string.to),
                                            isDate = true,
                                            isEditTextEnabled = isGraduated.value,
                                            trailingIcon = R.drawable.ic_polygon,
                                            onValueChange = { endDate.value = it }
                                        )
                                    }
                                }

                            }
                        }

                        R.string.gpa -> {
                            Row(modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween) {
                                Column(
                                    modifier = Modifier.weight(1f).padding(horizontal = 10.dp)
                                ) {
                                    Text(text = stringResource(R.string.gpa), color = GrayFont, fontSize = 14.sp,
                                        modifier = Modifier.padding(start = 10.dp))
                                    Spacer(modifier = Modifier.height(4.dp))
                                    CustomEditText(
                                        label = stringResource(R.string.gpa),
                                        list = listOf(
                                            4.0, 3.9, 3.8, 3.7, 3.6, 3.5, 3.4, 3.3,
                                            3.2, 3.1, 3.0, 2.9, 2.8, 2.7, 2.6, 2.5,
                                            2.4, 2.3, 2.2, 2.1, 2.0, 1.9, 1.8, 1.7,
                                            1.6, 1.5, 1.4, 1.3, 1.2, 1.1, 1.0, 0.9,
                                            0.8, 0.7, 0.6, 0.5, 0.4, 0.3, 0.2, 0.1, 0.0
                                        ).map { it.toString() }
                                        ,
                                        onValueChange = { gpa.value = it }
                                    )
                                }
                                Spacer(modifier = Modifier.width(10.dp))
                                Column (
                                    modifier = Modifier.weight(1f).padding(horizontal = 10.dp)
                                ){
                                    Text(text = stringResource(R.string.total), color = GrayFont, fontSize = 14.sp,
                                        modifier = Modifier.padding(start = 10.dp))
                                    Spacer(modifier = Modifier.height(4.dp))
                                    CustomEditText(
                                        label = stringResource(R.string.total),
                                        keyboardType = KeyboardType.Number,
                                        trailingIcon = R.drawable.ic_polygon,
                                        onValueChange = { total.value = it }
                                    )
                                }

                            }
                        }

                        R.string.graduated -> {
                            Column (
                                modifier = Modifier.fillMaxWidth().padding(horizontal = 10.dp),
                                horizontalAlignment = Alignment.Start
                            ){
                                CustomToggleSwitch(label = stringResource(R.string.graduated),
                                    isChecked = isGraduated.value){
                                    isGraduated.value = it
                                }
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
                                    label = stringResource(label),
                                    keyboardType = KeyboardType.Text,
                                    onValueChange = { stateMap[label]?.value = it }
                                )
                            }
                        }
                    }
                }

                AppButton(text = stringResource(R.string.save),
                    isLoading = isLoading) {
                    val educationDto =  EducationDto(
                        education = education.value,
                        course = course.value,
                        university = university.value,
                        startDate = startDate.value,
                        endDate = endDate.value,
                        graduated = isGraduated.value,
                        gpa = gpa.value.toFloat(),
                        total = total.value.toFloat(),
                        description = description.value,
                        userId = -1
                    )
                    if (isValid(educationDto))
                        educationViewModel.createEducation(educationDto)
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
fun isValid(educationDto: EducationDto): Boolean {
    var valid =  educationDto.education.isNotBlank() &&
            educationDto.course.isNotBlank() &&
            educationDto.university.isNotBlank() &&
            educationDto.startDate.isNotBlank() &&
            educationDto.gpa > 0 &&
            educationDto.total > 0
    if (valid && educationDto.graduated)  valid = educationDto.endDate.isNotBlank()

    val dateComparator = DateComparator(dateFormat = "yyyy-MM-dd")

    if (valid && educationDto.graduated){
        dateComparator.isAfter(educationDto.startDate, educationDto.endDate)
        valid = false
    }
    return valid  && !dateComparator.isAfterCurrentDate(educationDto.startDate)
}