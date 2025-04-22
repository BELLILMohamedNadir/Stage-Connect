package com.example.stageconnect.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.stageconnect.R
import com.example.stageconnect.data.model.Offer
import com.example.stageconnect.ui.theme.BackgroundGray
import com.example.stageconnect.ui.theme.BackgroundGray_
import com.example.stageconnect.ui.theme.BlackFont
import com.example.stageconnect.ui.theme.Blue
import com.example.stageconnect.ui.theme.BlueBackground
import com.example.stageconnect.ui.theme.GrayFont
import com.example.stageconnect.ui.theme.LibreBaskerVilleBold

@Composable
fun CustomOfferCard(modifier: Modifier = Modifier,
                    offer: Offer,
                    isSaveIconDisable: Boolean = false,
                    showLabel:Boolean = false,
                    showDate: Boolean = false,
                    onSavedClick: () -> Unit = {},
                    onCardClick: (Offer) -> Unit = {}) {
    var savedIcon by remember{ mutableIntStateOf(if (offer.isSaved) R.drawable.ic_save_filled else R.drawable.ic_save) }
    Box(contentAlignment = Alignment.TopCenter){
        Card(
            modifier = modifier
                .height(if (!showDate) 205.dp else 240.dp)
                .width(280.dp)
                .clip(shape = RoundedCornerShape(30.dp))
                .background(color = Color.White)
                .border(width = 1.dp, color = BackgroundGray_, shape = RoundedCornerShape(30.dp)),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        ){
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .clickable {
                        onCardClick(offer)
                    }
                    .background(color = Color.White)
                    .padding(vertical = 16.dp, horizontal = 12.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                //logo, position, company and save icon
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        modifier = Modifier.weight(1f)
                    ) {
                        //logo
                        Card(
                            modifier = Modifier
                                .height(45.dp)
                                .width(45.dp)
                                .background(color = Color.White)
                                .border(
                                    width = 1.dp,
                                    color = BackgroundGray,
                                    shape = RoundedCornerShape(10.dp)
                                ),
                            elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
                            shape = RoundedCornerShape(10.dp)
                        ){
                            Box(modifier = Modifier
                                .fillMaxSize()
                                .background(color = Color.White), contentAlignment = Alignment.Center) {
                                Image(painter = painterResource(offer.logo),
                                    contentDescription = "company image",
                                    modifier = Modifier
                                        .height(19.dp)
                                        .width(22.dp),
                                    contentScale = ContentScale.Crop)
                            }
                        }

                        Spacer(modifier = Modifier.width(10.dp))
                        //position and company
                        Column(
                        ) {
                            Text(text = offer.position,
                                fontFamily = LibreBaskerVilleBold,
                                fontWeight = FontWeight.W600,
                                fontSize = 16.sp,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                                color = Color.Black)
                            Text(text = offer.company,
                                fontFamily = LibreBaskerVilleBold,
                                fontWeight = FontWeight.W500,
                                fontSize = 15.sp,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                                color = BlackFont)
                        }
                    }
                    IconButton(onClick = {
                        if (!isSaveIconDisable){
                            savedIcon = if (savedIcon == R.drawable.ic_save) {
                                R.drawable.ic_save_filled
                            }else{
                                R.drawable.ic_save
                            }
                            onSavedClick()
                        }else{
                            savedIcon = R.drawable.ic_save_filled
                        }
                    }) {
                        Icon(painter = painterResource(savedIcon),
                            contentDescription = "saved icon",
                            tint = Color.Unspecified)
                        onSavedClick()
                    }
                }
                Spacer(modifier = Modifier.height(10.dp))
                Spacer(modifier = Modifier
                    .height(1.dp)
                    .width(220.dp)
                    .background(color = BackgroundGray_))
                Spacer(modifier = Modifier.height(20.dp))

                Row {
                    Spacer(modifier = Modifier.width(55.dp))
                    //Place , salary and options
                    Column(modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(text = offer.location,
                            fontFamily = LibreBaskerVilleBold,
                            fontWeight = FontWeight.W500,
                            fontSize = 14.sp,
                            color = BlackFont,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis)
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(text = offer.salary,
                            fontFamily = LibreBaskerVilleBold,
                            fontWeight = FontWeight.W500,
                            fontSize = 12.sp,
                            color = Blue,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis)
                        Spacer(modifier = Modifier.height(12.dp))
                        LazyRow(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            items(offer.options){ option ->
                                CardOption(option = option)
                            }
                        }
                    }
                }
                if (showDate){
                    Spacer(modifier = Modifier.height(12.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.End
                    ) {
                        Text(
                            text = offer.postedDate,
                            fontWeight = FontWeight.W400,
                            fontSize = 12.sp,
                            fontFamily = LibreBaskerVilleBold,
                            color = GrayFont
                        )
                    }
                }

            }
        }

        if (showLabel && savedIcon == R.drawable.ic_save_filled){
            Card(
                modifier = modifier
                    .height(50.dp)
                    .width(150.dp)
                    .offset(y = (-40).dp)
                    .clip(shape = RoundedCornerShape(15.dp))
                    .background(color = BlueBackground)
                    .border(width = 1.dp, color = Blue, shape = RoundedCornerShape(15.dp)),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
            ){
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CustomCheckbox(
                        label = stringResource(R.string.job_saved),
                        labelSize = 14.sp,
                        isEnabled = false,
                        isChecked = true)
                }
            }
        }
    }
}

@Composable
fun CardOption(modifier: Modifier = Modifier, option: String) {
    Card(
        modifier = Modifier
            .height(30.dp)
            .clip(shape = RoundedCornerShape(10.dp))
            .background(color = Color.White)
            .border(width = 1.dp, color = BackgroundGray_, shape = RoundedCornerShape(10.dp)),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
    ){
        Box(modifier = Modifier.fillMaxSize().background(color = Color.White).padding(horizontal = 10.dp),
            contentAlignment = Alignment.Center){
            Text(text = option,
                fontFamily = LibreBaskerVilleBold,
                fontWeight = FontWeight.W400,
                fontSize = 11.sp,
                color = GrayFont,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                textAlign = TextAlign.Center)
        }
    }
}