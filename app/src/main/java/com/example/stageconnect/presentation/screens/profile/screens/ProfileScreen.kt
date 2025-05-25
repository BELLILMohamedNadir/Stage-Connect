package com.example.stageconnect.presentation.screens.profile.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.stageconnect.R
import com.example.stageconnect.presentation.components.CustomCircularProgressIndicator
import com.example.stageconnect.presentation.components.NotFound
import com.example.stageconnect.presentation.components.ProfileImage
import com.example.stageconnect.presentation.navigation.Screen
import com.example.stageconnect.presentation.screens.profile.components.CustomCard
import com.example.stageconnect.presentation.screens.profile.viewmodels.ProfileViewModel
import com.example.stageconnect.ui.theme.BackgroundGray_
import com.example.stageconnect.ui.theme.GrayFont

@Composable
fun ProfileScreen(modifier: Modifier = Modifier,
                  navController: NavHostController,
                  profileViewModel: ProfileViewModel) {

    val items = listOf(
        Triple(stringResource(R.string.contact_information) , R.drawable.ic_contact_information) {
            navController.navigate(Screen.ContactInformation.route)
        },
        Triple(stringResource(R.string.summary) , R.drawable.ic_summary, {
            navController.navigate(Screen.Summary.route)
        }),
        Triple(stringResource(R.string.work_experience) , R.drawable.ic_work_experience, {
            navController.navigate(Screen.WorkExperience.route)
        }),
        Triple(stringResource(R.string.education_) , R.drawable.ic_education, {
            navController.navigate(Screen.Education.route)
        }),
        Triple(stringResource(R.string.projects) , R.drawable.ic_project, {
            navController.navigate(Screen.Project.route)
        }),
        Triple(stringResource(R.string.certifications) , R.drawable.ic_certification, {
            navController.navigate(Screen.Certification.route)
        }),
        Triple(stringResource(R.string.internships) , R.drawable.ic_internship, {
            navController.navigate(Screen.Internship.route)
        }),
        Triple(stringResource(R.string.skills) , R.drawable.ic_skill, {
            navController.navigate(Screen.Skill.route)
        }),
        Triple(stringResource(R.string.languages) , R.drawable.ic_language, {
            navController.navigate(Screen.Language.route)
        }),
        Triple(stringResource(R.string.cv_resume) , R.drawable.ic_pdf, {
            navController.navigate(Screen.CvResume.route)
        }),
    )

    val user = profileViewModel.user.value

    if (user != null){
        Column(
            modifier = modifier.fillMaxSize().padding(vertical = 8.dp, horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            //Header  photo, full name, current position and the pen icon
            Row(modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween){
                Row(modifier = Modifier.weight(1f),
                    verticalAlignment = Alignment.CenterVertically) {
                    ProfileImage(user.photo, 80.dp)
                    Spacer(modifier = Modifier.width(10.dp))
                    Column {
                        Text(
                            text = "${user.name} ${user.firstName}",
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 18.sp,
                            color = Color.Black,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis)
                        Spacer(modifier = Modifier.height(10.dp))
                        Text(
                            text = user.currentPosition ?: "",
                            fontWeight = FontWeight.Normal,
                            fontSize = 16.sp,
                            maxLines = 1,
                            color = GrayFont,
                            overflow = TextOverflow.Ellipsis)
                    }
                }
                IconButton(onClick = {
                    navController.navigate(Screen.ProfileDetails.route)
                }) {
                    Icon(painter = painterResource(R.drawable.ic_pen),
                        contentDescription = "pen icon",
                        tint = Color.Unspecified)
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            Spacer(modifier = Modifier.fillMaxWidth(fraction = 0.9f).height(1.dp).background(color = BackgroundGray_))
            Spacer(modifier = Modifier.height(10.dp))

            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(16.dp),
                contentPadding = PaddingValues(vertical = 12.dp)
            ) {
                items(items){ (text, image, onClick) ->
                    CustomCard(
                        text = text,
                        image = image
                    ) {
                        onClick()
                    }
                }
            }
        }
    }else{
        NotFound(showMessage = false)
    }
}