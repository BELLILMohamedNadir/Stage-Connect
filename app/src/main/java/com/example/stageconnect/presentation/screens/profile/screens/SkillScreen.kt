package com.example.stageconnect.presentation.screens.profile.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.stageconnect.R
import com.example.stageconnect.presentation.components.AppButton
import com.example.stageconnect.presentation.components.CustomCardOption
import com.example.stageconnect.presentation.components.CustomEditText
import com.example.stageconnect.presentation.components.CustomMessage
import com.example.stageconnect.presentation.components.ObserveResult
import com.example.stageconnect.presentation.screens.profile.viewmodels.ProfileViewModel
import com.example.stageconnect.ui.theme.GrayFont

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun SkillScreen(modifier: Modifier = Modifier,
                viewModel: ProfileViewModel,
                onNext: () -> Unit
) {
    val skills = remember { mutableStateListOf<String>() }
    val isLoading = rememberSaveable { mutableStateOf(false) }
    val addSkillsResult by viewModel.addSkillsResult.observeAsState()

    ObserveResult(
        result = addSkillsResult,
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
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)) {
            Column (
                modifier = Modifier.fillMaxWidth().padding(horizontal = 10.dp),
                horizontalAlignment = Alignment.Start,
            ){
                Text(text = stringResource(R.string.skill), color = GrayFont, fontSize = 14.sp,
                    modifier = Modifier.fillMaxWidth().padding(start = 16.dp))
                Spacer(modifier = Modifier.height(10.dp))
                CustomEditText(
                    label = stringResource(R.string.skill),

                    trailingIconWithAction = true,
                    trailingIcon = R.drawable.ic_more,
                    keyboardType = KeyboardType.Text,
                    onValueChange = { /* handle address change */ },
                    onTrailingIconClick = {
                        skills.add(it.trim())
                        ""
                    }
                )
            }

            FlowRow(
                modifier = Modifier.fillMaxWidth().padding(start = 6.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp, Alignment.Start),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                skills.forEach { skill ->
                    CustomCardOption(option = skill, isDeletable = true, onDelete = {
                        skills.remove(it)
                    })
                }
            }
        }
        AppButton(text = stringResource(R.string.save), isLoading = isLoading) {
            if (skills.isNotEmpty()){
                viewModel.addSkills(skills)
            }
        }
    }
}