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
import com.example.stageconnect.data.dtos.InternshipDto
import com.example.stageconnect.domain.DateComparator
import com.example.stageconnect.presentation.components.AppButton
import com.example.stageconnect.presentation.components.CustomEditText
import com.example.stageconnect.presentation.components.CustomTextArea
import com.example.stageconnect.presentation.components.CustomToggleSwitch
import com.example.stageconnect.presentation.components.CustomMessage
import com.example.stageconnect.presentation.components.ObserveResult
import com.example.stageconnect.presentation.screens.profile.viewmodels.InternshipViewModel
import com.example.stageconnect.ui.theme.GrayFont

@Composable
fun InternshipScreen(modifier: Modifier = Modifier,
                  internshipViewModel: InternshipViewModel = hiltViewModel(),
                     onNext: () -> Unit
) {

    val inputFields = listOf(
        R.string.title,
        R.string.organization,
        R.string.role_optional,
        R.string.from,
        R.string.current,
        R.string.description_optional,
        R.string.organization_website_link_optional,
    )

    val title = rememberSaveable { mutableStateOf("") }
    val organization = rememberSaveable { mutableStateOf("") }
    val role = rememberSaveable { mutableStateOf("") }
    val startDate = rememberSaveable { mutableStateOf("") }
    val endDate = rememberSaveable { mutableStateOf("") }
    val current = rememberSaveable { mutableStateOf(false) }
    val description = rememberSaveable { mutableStateOf("") }
    val websiteLink = rememberSaveable { mutableStateOf("") }
    val isLoading = rememberSaveable { mutableStateOf(false) }
    var showErrorMessage by rememberSaveable { mutableStateOf(false) }

    val stateMap = mapOf(
        R.string.title to title,
        R.string.organization to organization,
        R.string.role_optional to role,
        R.string.organization_website_link_optional to websiteLink,
    )

    val createInternshipResult by internshipViewModel.createInternshipResult.observeAsState()

    ObserveResult(
        result = createInternshipResult,
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
                inputFields.forEach { label ->

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
                                if (!current.value){
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
                                            isEditTextEnabled = !current.value,
                                            trailingIcon = R.drawable.ic_polygon,
                                            onValueChange = { endDate.value = it }
                                        )
                                    }
                                }

                            }
                        }

                        R.string.current -> {
                            Column (
                                modifier = Modifier.fillMaxWidth().padding(horizontal = 10.dp),
                                horizontalAlignment = Alignment.Start
                            ){
                                CustomToggleSwitch(label = stringResource(R.string.current), isChecked = current.value){
                                    current.value = it
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
                    val internshipDto =  InternshipDto(
                        title = title.value,
                        organization = organization.value,
                        role = role.value,
                        startDate = startDate.value,
                        endDate = endDate.value,
                        current = current.value,
                        description = description.value,
                        organizationWebsite = websiteLink.value,
                        userId = -1
                    )
                    if (isValid(internshipDto))
                        internshipViewModel.createInternship(internshipDto)
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

fun isValid(internshipDto: InternshipDto): Boolean {
    var valid =  internshipDto.title.isNotBlank() &&
            internshipDto.organization.isNotBlank() &&
            internshipDto.startDate.isNotBlank()

    if (valid && !internshipDto.current)  valid = internshipDto.endDate.isNotBlank()

    val dateComparator = DateComparator(dateFormat = "yyyy-MM-dd")

    if (valid && !internshipDto.current){
        dateComparator.isAfter(internshipDto.startDate, internshipDto.endDate)
        valid = false
    }
    return valid && !dateComparator.isAfterCurrentDate(internshipDto.startDate)
}