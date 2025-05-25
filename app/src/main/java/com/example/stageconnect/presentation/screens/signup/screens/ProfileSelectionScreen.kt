package com.example.stageconnect.presentation.screens.signup.screens

import android.util.Log
import androidx.compose.foundation.clickable
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
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.stageconnect.R
import com.example.stageconnect.domain.model.enums.ROLE
import com.example.stageconnect.presentation.components.AppButton
import com.example.stageconnect.presentation.components.AppLogo
import com.example.stageconnect.presentation.screens.signup.components.CustomCardView
import com.example.stageconnect.presentation.screens.signup.viewmodels.RegisterViewModel
import com.example.stageconnect.ui.theme.BlueFont
import com.example.stageconnect.ui.theme.GrayFont
import com.example.stageconnect.ui.theme.LibreBaskerVilleBold
import com.example.stageconnect.ui.theme.ReemKufiInkRegular

@Composable
fun ProfileSelectionScreen(modifier: Modifier = Modifier,
                           registerViewModel: RegisterViewModel,
                           onNext: () -> Unit) {
    var role by remember { mutableStateOf(ROLE.STUDENT) }

    Column(
        modifier = modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        AppLogo()
        Spacer(modifier = Modifier.height(10.dp))
        Column(
            modifier = Modifier.padding(horizontal = 10.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = stringResource(R.string.choose_your_profile_type),
                fontFamily = ReemKufiInkRegular,
                fontSize = 28.sp,
                color = Color.Black,
                fontWeight = FontWeight.W700
            )
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = stringResource(R.string.account_creation_profile_description),
                fontFamily = LibreBaskerVilleBold,
                fontSize = 14.sp,
                color = GrayFont,
                fontWeight = FontWeight.W500,
                textAlign = TextAlign.Center
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween) {
            var selectedIndex by remember { mutableStateOf(1) }
            CustomCardView(
                index = 1,
                selectedIndex = selectedIndex == 1,
                icon = R.drawable.ic_bag,
                title = stringResource(R.string.you_are_an_intern),
                description = stringResource(R.string.i_want_to_find_an_internship)
            ) {
                selectedIndex = 1
                role = ROLE.STUDENT
            }
            Spacer(modifier = Modifier.height(16.dp))
            CustomCardView(
                index = 2,
                selectedIndex = selectedIndex == 2,
                icon = R.drawable.ic_user,
                title = stringResource(R.string.you_are_a_recruter),
                description = stringResource(R.string.i_want_to_find_interns)
            ) {
                selectedIndex = 2
                role = ROLE.RECRUITER
            }
            Spacer(modifier = Modifier.height(16.dp))
            CustomCardView(
                index = 3,
                selectedIndex = selectedIndex == 3,
                icon = R.drawable.ic_user,
                title = stringResource(R.string.you_are_a_responsible),
                description = stringResource(R.string.i_am_an_interns_supervisor)
            ) {
                selectedIndex = 3
                role = ROLE.ESTABLISHMENT
            }
        }
        Spacer(modifier = Modifier.height(10.dp))
        AppButton(text = stringResource(R.string.continue_)) {
            registerViewModel.setRole(role)
            onNext()
        }
    }
}