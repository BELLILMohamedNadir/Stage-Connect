package com.example.stageconnect.presentation.components

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.stageconnect.R
import com.example.stageconnect.data.model.Offer
import com.example.stageconnect.presentation.screens.jobdetails.screens.JobDescriptionScreen
import com.example.stageconnect.presentation.screens.jobdetails.screens.MinimumQualificationScreen
import com.example.stageconnect.ui.theme.Blue
import com.example.stageconnect.ui.theme.GrayFont

@Composable
fun CustomTabRow(modifier: Modifier = Modifier, offer: Offer) {
    val tabTitles = listOf(stringResource(R.string.job_description), stringResource(R.string.minimum_qualification))
    var selectedTabIndex by remember { mutableIntStateOf(0) }

    Column(
        modifier = modifier
    ) {
        TabRow(selectedTabIndex = selectedTabIndex) {
            tabTitles.forEachIndexed { index, title ->
                Tab(
                    text = { Text(title) },
                    selected = selectedTabIndex == index,
                    onClick = { selectedTabIndex = index },
                    unselectedContentColor = GrayFont,
                    selectedContentColor = Blue
                )
            }
        }

        Crossfade(targetState = selectedTabIndex) { index ->
            when (index) {
                0 -> JobDescriptionScreen(offer = offer)
                1 -> MinimumQualificationScreen()
            }
        }
    }

}