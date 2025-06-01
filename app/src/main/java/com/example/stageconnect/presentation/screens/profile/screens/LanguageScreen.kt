package com.example.stageconnect.presentation.screens.profile.screens

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
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.stageconnect.R
import com.example.stageconnect.data.dtos.LanguageDto
import com.example.stageconnect.presentation.components.AppButton
import com.example.stageconnect.presentation.components.CustomEditText
import com.example.stageconnect.presentation.components.CustomMessage
import com.example.stageconnect.presentation.components.ObserveResult
import com.example.stageconnect.presentation.screens.profile.viewmodels.LanguageViewModel
import com.example.stageconnect.ui.theme.GrayFont

@Composable
fun LanguageScreen(modifier: Modifier = Modifier,
                  languageViewModel: LanguageViewModel = hiltViewModel(),
                   onNext: () -> Unit
) {
    val context = LocalContext.current
    val languages = context.resources.getStringArray(R.array.languages_array).toList()
    val proficiencies = context.resources.getStringArray(R.array.proficiencies_array).toList()


    val language = remember { mutableStateOf("") }
    val proficiency = remember { mutableStateOf("") }
    val isLoading = remember { mutableStateOf(false) }
    var showErrorMessage by remember { mutableStateOf(false) }


    val createLanguageResult by languageViewModel.createLanguageResult.observeAsState()

    ObserveResult(
        result = createLanguageResult,
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

    Column(
        modifier = modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)) {
            Column (
                modifier = Modifier.fillMaxWidth().padding(horizontal = 10.dp),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.SpaceBetween
            ){
                Text(text = stringResource(R.string.language), color = GrayFont, fontSize = 14.sp,
                    modifier = Modifier.fillMaxWidth().padding(start = 16.dp))
                Spacer(modifier = Modifier.height(10.dp))
                CustomEditText(
                    label = stringResource(R.string.language),
                    list = languages,
                    keyboardType = KeyboardType.Text,
                    onValueChange = {language.value = it}
                )

                Text(text = stringResource(R.string.proficiency), color = GrayFont, fontSize = 14.sp,
                    modifier = Modifier.fillMaxWidth().padding(start = 16.dp))
                Spacer(modifier = Modifier.height(10.dp))
                CustomEditText(
                    label = stringResource(R.string.proficiency),
                    list = proficiencies,
                    keyboardType = KeyboardType.Text,
                    onValueChange = {proficiency.value = it}
                )
            }
        }
        AppButton(text = stringResource(R.string.save),
            isLoading = isLoading) {
            val languageDto =  LanguageDto(
                language = language.value,
                proficiency = proficiency.value,
                userId = -1
            )
            if (languageDto.language.isNotBlank() && languageDto.proficiency.isNotBlank())
                languageViewModel.createLanguage(languageDto)
            else
                showErrorMessage = true
        }
    }
    if (showErrorMessage){
        showErrorMessage = false
        CustomMessage.Show(stringResource(R.string.add_necessary_data))
    }
}