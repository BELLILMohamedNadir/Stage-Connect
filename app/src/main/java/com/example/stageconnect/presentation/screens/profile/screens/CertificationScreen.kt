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
import com.example.stageconnect.data.dtos.CertificationDto
import com.example.stageconnect.domain.DateComparator
import com.example.stageconnect.presentation.components.AppButton
import com.example.stageconnect.presentation.components.CustomEditText
import com.example.stageconnect.presentation.components.CustomToggleSwitch
import com.example.stageconnect.presentation.components.CustomMessage
import com.example.stageconnect.presentation.components.ObserveResult
import com.example.stageconnect.presentation.screens.profile.viewmodels.CertificationViewModel
import com.example.stageconnect.ui.theme.GrayFont

@Composable
fun CertificationScreen(modifier: Modifier = Modifier,
                        certificationViewModel: CertificationViewModel = hiltViewModel(),
                        onNext: () -> Unit
) {
    val inputFields = listOf(
        R.string.title,
        R.string.organization,
        R.string.date_of_issue,
        R.string.this_credential_will_not_expire,
        R.string.credential_id_optional,
        R.string.credential_url_optional
    )
    val title = rememberSaveable { mutableStateOf("") }
    val organization = rememberSaveable { mutableStateOf("") }
    val dateOfIssue = rememberSaveable { mutableStateOf("") }
    val dateOfExpiration = rememberSaveable { mutableStateOf("") }
    val isCredentialNotWillExpire = rememberSaveable { mutableStateOf(true) }
    val credentialId = rememberSaveable { mutableStateOf("") }
    val credentialUrl = rememberSaveable { mutableStateOf("") }
    val isLoading = rememberSaveable { mutableStateOf(false) }
    var showErrorMessage by rememberSaveable { mutableStateOf(false) }

    val stateMap = mapOf(
        R.string.title to title,
        R.string.organization to organization,
        R.string.date_of_issue to dateOfIssue,
        R.string.expiration_date to dateOfExpiration,
        R.string.credential_id_optional to credentialId,
        R.string.credential_url_optional to credentialUrl
    )

    val createCertificationResult by certificationViewModel.createCertificationResult.observeAsState()

    ObserveResult(
        result = createCertificationResult,
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
                        R.string.date_of_issue -> {
                            Row(modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween) {
                                Column(
                                    modifier = Modifier.weight(1f).padding(horizontal = 10.dp)
                                ) {
                                    Text(text = stringResource(R.string.date_of_issue), color = GrayFont, fontSize = 14.sp,
                                        modifier = Modifier.padding(start = 10.dp))
                                    Spacer(modifier = Modifier.height(4.dp))
                                    CustomEditText(
                                        label = stringResource(R.string.date_of_issue),
                                        isDate = true,
                                        trailingIcon = R.drawable.ic_polygon,
                                        onValueChange = { dateOfIssue.value = it }
                                    )
                                }
                                if (!isCredentialNotWillExpire.value){
                                    Spacer(modifier = Modifier.width(10.dp))
                                    Column (
                                        modifier = Modifier.weight(1f).padding(horizontal = 10.dp)
                                    ){
                                        Text(text = stringResource(R.string.expiration_date), color = GrayFont, fontSize = 14.sp,
                                            modifier = Modifier.padding(start = 10.dp))
                                        Spacer(modifier = Modifier.height(4.dp))
                                        CustomEditText(
                                            label = stringResource(R.string.expiration_date),
                                            isDate = true,
                                            trailingIcon = R.drawable.ic_polygon,
                                            isEditTextEnabled = !isCredentialNotWillExpire.value,
                                            onValueChange = { dateOfExpiration.value = it }
                                        )
                                    }
                                }

                            }
                        }

                        R.string.this_credential_will_not_expire -> {
                            Column (
                                modifier = Modifier.fillMaxWidth().padding(horizontal = 10.dp),
                                horizontalAlignment = Alignment.Start
                            ){
                                CustomToggleSwitch(
                                    label = stringResource(R.string.this_credential_will_not_expire),
                                    isChecked = isCredentialNotWillExpire.value){
                                    isCredentialNotWillExpire.value = it
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
                                    resetText = stateMap[label]!!,
                                    onValueChange = { stateMap[label]?.value = it }
                                )
                            }
                        }
                    }
                }

                AppButton(text = stringResource(R.string.save),
                    isLoading = isLoading) {
                    val certificationDto =  CertificationDto(
                        title = title.value,
                        organization = organization.value,
                        dateOfIssue = dateOfIssue.value,
                        expirationDate = dateOfExpiration.value,
                        credentialWillNotExpire = isCredentialNotWillExpire.value,
                        credentialId = credentialId.value,
                        credentialUrl = credentialUrl.value,
                        userId = -1
                    )
                    if (isValid(certificationDto))
                        certificationViewModel.createCertification(certificationDto)
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
fun isValid(certificationDto: CertificationDto): Boolean {
    var valid =  certificationDto.title.isNotBlank() &&
            certificationDto.organization.isNotBlank() &&
            certificationDto.dateOfIssue.isNotBlank()

    if (valid && !certificationDto.credentialWillNotExpire) valid = certificationDto.expirationDate.isNotBlank()

    val dateComparator = DateComparator(dateFormat = "yyyy-MM-dd")

    if (valid && !certificationDto.credentialWillNotExpire){
        dateComparator.isAfter(certificationDto.dateOfIssue, certificationDto.expirationDate)
        valid = false
    }
    return valid && !dateComparator.isAfterCurrentDate(certificationDto.dateOfIssue)
}