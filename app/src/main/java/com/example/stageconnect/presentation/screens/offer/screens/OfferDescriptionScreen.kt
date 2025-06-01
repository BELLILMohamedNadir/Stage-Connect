package com.example.stageconnect.presentation.screens.offer.screens

import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.net.toUri
import com.example.stageconnect.R
import com.example.stageconnect.data.dtos.OfferDto
import com.example.stageconnect.presentation.components.AppButton
import com.example.stageconnect.presentation.components.CustomCardOption
import com.example.stageconnect.presentation.components.CustomMessage
import com.example.stageconnect.presentation.navigation.Screen
import com.example.stageconnect.ui.theme.Blue
import com.example.stageconnect.ui.theme.GrayFont
import com.example.stageconnect.ui.theme.LibreBaskerVilleBold

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun JobDescriptionScreen(
    modifier: Modifier = Modifier,
    offer: OfferDto,
    source: String,
    onApply: (OfferDto) -> Unit
) {
    val context = LocalContext.current
    var showErrorMessage by remember { mutableStateOf(false) }
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(top = 24.dp, start = 5.dp)
    ) {
        //Job Description
        Text(
            text = stringResource(R.string.job_description),
            fontFamily = LibreBaskerVilleBold,
            fontWeight = FontWeight.W600,
            color = Color.Black,
            fontSize = 16.sp,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = offer.jobDescription,
            modifier = Modifier.padding(start = 6.dp),
            fontFamily = LibreBaskerVilleBold,
            fontWeight = FontWeight.W400,
            color = GrayFont,
            fontSize = 12.sp,
            textAlign = TextAlign.Start
        )

        //Requirements and skills
        Spacer(modifier = Modifier.height(15.dp))
        Text(
            text = stringResource(R.string.requirements_and_skills),
            fontFamily = LibreBaskerVilleBold,
            fontWeight = FontWeight.W600,
            color = Color.Black,
            fontSize = 16.sp,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(10.dp))
        Column(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = ". ${offer.requirementSkills}",
                modifier = Modifier.padding(start = 6.dp),
                fontFamily = LibreBaskerVilleBold,
                fontWeight = FontWeight.W400,
                color = GrayFont,
                fontSize = 12.sp,
                textAlign = TextAlign.Center
            )
        }

        //Education
        Spacer(modifier = Modifier.height(15.dp))
        Text(
            text = stringResource(R.string.education),
            fontFamily = LibreBaskerVilleBold,
            fontWeight = FontWeight.W600,
            color = Color.Black,
            fontSize = 16.sp,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = when (offer.education.size) {
                0 -> ""
                1 -> offer.education.first()
                else -> offer.education.joinToString(", ")
            },
            modifier = Modifier.padding(start = 6.dp),
            fontFamily = LibreBaskerVilleBold,
            fontWeight = FontWeight.W400,
            color = GrayFont,
            fontSize = 12.sp,
            textAlign = TextAlign.Center
        )

        //Key Skills
        Spacer(modifier = Modifier.height(15.dp))
        Text(
            text = stringResource(R.string.key_skills),
            fontFamily = LibreBaskerVilleBold,
            fontWeight = FontWeight.W600,
            color = Color.Black,
            fontSize = 16.sp,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(10.dp))
        FlowRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 6.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp, Alignment.Start),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            offer.keySkills.forEach { skill ->
                CustomCardOption(option = skill)
            }
        }

        //Job Summary
        Spacer(modifier = Modifier.height(15.dp))
        Text(
            text = stringResource(R.string.job_summary),
            fontFamily = LibreBaskerVilleBold,
            fontWeight = FontWeight.W600,
            color = Color.Black,
            fontSize = 16.sp,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(10.dp))
        DetailRow(label = stringResource(R.string.role), value = offer.position)
        DetailRow(
            label = stringResource(R.string.job_function),
            value = when (offer.jobFunction.size) {
                0 -> ""
                1 -> offer.education.first()
                else -> offer.education.joinToString(", ")
            }
        )
        DetailRow(
            label = stringResource(R.string.employment_type),
            value = when (offer.employmentTypes.size) {
                0 -> ""
                1 -> offer.education.first()
                else -> offer.education.joinToString(", ")
            }
        )
        DetailRow(label = stringResource(R.string.website), value = offer.website ?: "") { link ->
            try {
                val intent = Intent(Intent.ACTION_VIEW, link.toUri())
                context.startActivity(intent)
            }catch (e: Exception){
                showErrorMessage = true
            }
        }

        //Company Description
        Spacer(modifier = Modifier.height(15.dp))
        Text(
            text = stringResource(R.string.company_description),
            fontFamily = LibreBaskerVilleBold,
            fontWeight = FontWeight.W600,
            color = Color.Black,
            fontSize = 16.sp,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = offer.companyDescription ?: "",
            modifier = Modifier.padding(start = 6.dp),
            fontFamily = LibreBaskerVilleBold,
            fontWeight = FontWeight.W400,
            color = GrayFont,
            fontSize = 12.sp,
            textAlign = TextAlign.Start
        )

        if (source != Screen.EstablishmentApplicationStatus.route){
            //  APPLY BUTTON
            Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                AppButton(
                    modifier = Modifier
                        .fillMaxWidth(0.85f)
                        .padding(top = 15.dp, bottom = 10.dp),
                    text = if (offer.isApplied) stringResource(R.string.applied) else stringResource(R.string.apply),
                    isEnable = !offer.isApplied
                ) {
                    if (!offer.isApplied)
                        onApply(offer)
                }
            }
        }
    }
    if (showErrorMessage){
        showErrorMessage = false
        CustomMessage.Show(message = stringResource(R.string.error_occurred))
    }

}

@Composable
fun DetailRow(label: String, value: String, onClick: (String) -> Unit = {}) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.Start
    ) {
        // Label column with fixed width
        Text(
            text = label,
            fontFamily = LibreBaskerVilleBold,
            fontSize = 13.sp,
            fontWeight = FontWeight.W600,
            modifier = Modifier
                .width(150.dp)
                .padding(start = 6.dp)
        )

        Spacer(modifier = Modifier.width(8.dp))

        Text(
            text = value,
            fontFamily = LibreBaskerVilleBold,
            fontSize = 12.sp,
            modifier = Modifier
                .weight(1f)
                .clickable { onClick(value) },
            color = if (label == stringResource(R.string.website)) Blue else GrayFont,
            overflow = TextOverflow.Ellipsis,
            maxLines = 2
        )
    }
}