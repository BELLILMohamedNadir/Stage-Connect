package com.example.stageconnect.presentation.screens.signup.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.stageconnect.R
import com.example.stageconnect.presentation.components.AppButton
import com.example.stageconnect.presentation.screens.signup.components.CustomCardWithCheckbox
import com.example.stageconnect.ui.theme.GrayFont
import com.example.stageconnect.ui.theme.LibreBaskerVilleBold

@Composable
fun ExpertiseSelectionScreen(modifier: Modifier = Modifier, onNext: () -> Unit) {
    val expertises = listOf(
        stringResource(R.string.expertise_accounting),
        stringResource(R.string.expertise_engineering),
        stringResource(R.string.expertise_it),
        stringResource(R.string.expertise_media),
        stringResource(R.string.expertise_sales),
        stringResource(R.string.expertise_management),
        stringResource(R.string.expertise_writing)
    )
    Column(
        modifier = modifier.fillMaxSize().padding(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        val selectedIndexes = remember { mutableStateOf(setOf<Int>()) }
        //Texts
        Text(
            text = stringResource(R.string.what_is_your_expertise),
            fontFamily = LibreBaskerVilleBold,
            fontWeight = FontWeight.W700,
            fontSize = 28.sp,
            color = Color.Black,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = stringResource(R.string.please_select_the_field_of_expertise),
            fontFamily = LibreBaskerVilleBold,
            fontWeight = FontWeight.W400,
            fontSize = 15.sp,
            color = GrayFont,
            textAlign = TextAlign.Center
        )
        //Expertises
        expertises.forEachIndexed { index, value ->
            val isSelected = index in selectedIndexes.value
            CustomCardWithCheckbox(
                index = index,
                selectedIndex = isSelected,
                label = value
            ) {
                selectedIndexes.value = if (isSelected) {
                    selectedIndexes.value - index // deselect
                } else {
                    selectedIndexes.value + index // select
                }
            }
        }
        //Button
        AppButton(
            text = stringResource(R.string.continue_)
        ) {onNext() }
    }
}