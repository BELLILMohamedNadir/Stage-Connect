package com.example.stageconnect.presentation.screens.establishmentstudent.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.stageconnect.R
import com.example.stageconnect.data.dtos.ApplicationDto
import com.example.stageconnect.data.dtos.StudentDto
import com.example.stageconnect.domain.model.enums.STATUS
import com.example.stageconnect.presentation.components.ProfileImage
import com.example.stageconnect.ui.theme.BackgroundGray
import com.example.stageconnect.ui.theme.BlackFont
import com.example.stageconnect.ui.theme.Blue
import com.example.stageconnect.ui.theme.GreenBackground
import com.example.stageconnect.ui.theme.GreenFont
import com.example.stageconnect.ui.theme.LibreBaskerVilleBold
import com.example.stageconnect.ui.theme.RedBackground
import com.example.stageconnect.ui.theme.RedFont
import com.example.stageconnect.ui.theme.SoftBlue

@Composable
fun CustomEstablishmentStudentCard(modifier: Modifier = Modifier,
                                   circleShape: Boolean = false,
                                   studentDto: StudentDto,
                                   onIconCLick: (StudentDto) -> Unit = {}) {

    Column(
        modifier = modifier
            .height(80.dp)
            .fillMaxWidth(fraction = 0.9f)
            .background(Color.White),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row (
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ){
            Row(modifier = Modifier.weight(1f)){
                //photo
                Column(modifier = Modifier.align(alignment = Alignment.Top),
                    verticalArrangement = Arrangement.Top) {
                    ProfileImage(studentDto.photo, 64.dp, circleShape = circleShape)
                }

                Spacer(modifier = Modifier.width(10.dp))
                //position and company
                Column(
                    modifier = Modifier.fillMaxHeight(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    Column (
                        modifier = Modifier.align(Alignment.Start)
                    ){
                        Text(
                            text = "${studentDto.name} ${studentDto.firstName}",
                            fontFamily = LibreBaskerVilleBold,
                            fontWeight = FontWeight.W600,
                            fontSize = 16.sp,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            color = Color.Black)
                        Text(text = "${studentDto.currentPosition}",
                            fontFamily = LibreBaskerVilleBold,
                            fontWeight = FontWeight.W500,
                            fontSize = 15.sp,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            color = BlackFont)
                    }
                }
            }
        }

    }
}
