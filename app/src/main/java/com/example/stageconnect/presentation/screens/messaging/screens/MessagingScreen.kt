package com.example.stageconnect.presentation.screens.messaging.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.stageconnect.R
import com.example.stageconnect.presentation.components.CustomTopAppBar
import com.example.stageconnect.presentation.components.CustomMessage
import com.example.stageconnect.presentation.components.NotFound
import com.example.stageconnect.presentation.components.ObserveResult
import com.example.stageconnect.presentation.screens.messaging.components.CustomMessageCard
import com.example.stageconnect.presentation.screens.messaging.viewmodel.MessagingViewModel
import com.example.stageconnect.presentation.screens.messaging.viewmodel.RoomViewModel
import com.example.stageconnect.ui.theme.BackgroundGray
import com.example.stageconnect.ui.theme.GrayFont

@Composable
fun MessagingScreen(modifier: Modifier = Modifier,
                    roomViewModel: RoomViewModel,
                    messagingViewModel: MessagingViewModel = hiltViewModel(),
                    onDismiss: () -> Unit) {

    val roomDto = roomViewModel.room.value
    val userId = roomViewModel.getUserId()
    val isLoading = remember { mutableStateOf(false) }
    var message by remember { mutableStateOf("") }
    val messages by messagingViewModel.messages.collectAsState()

    val getAllMessagesResult by roomViewModel.getAllMessagesResult.observeAsState()

    LaunchedEffect(Unit) {
        roomViewModel.getAllMessages()
        messagingViewModel.connect(roomId = roomDto!!.id)
    }

    ObserveResult(
        result = getAllMessagesResult,
        onLoading = {isLoading.value = true},
        onSuccess = {
            isLoading.value = false
            messagingViewModel.clearData()
            it.let {
                messagingViewModel.addPastMessages(it)
            }
        },
        onError = {
            isLoading.value = false
            CustomMessage.Show(stringResource(R.string.error_occurred))
        }
    )

    if (roomDto != null){
        if (!isLoading.value){
            Scaffold(
                topBar = {
                    CustomTopAppBar(
                        onDismiss = onDismiss,
                        navigationIcon = R.drawable.ic_arrow_back,
                        title = roomDto.sender
                    )
                }
            ) {innerPadding ->
                Spacer(modifier = Modifier.fillMaxWidth().background(BackgroundGray))
                Column(modifier = Modifier.fillMaxSize().padding(innerPadding)) {
                    LazyColumn(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxWidth(),
                        reverseLayout = true,
                        verticalArrangement = Arrangement.spacedBy(12.dp, Alignment.Bottom),
                        contentPadding = PaddingValues(
                            start = 16.dp,
                            end = 16.dp,
                            top = 8.dp,
                            bottom = 10.dp
                        )
                    ) {
                        items(messages!!.reversed()){
                            CustomMessageCard(messageDto = it!!, userId = userId)
                        }
                    }

                    // Input bar (message + send button)
                    Card(
                        modifier = Modifier
                            .fillMaxWidth(fraction = 0.95f)
                            .padding(horizontal = 12.dp, vertical = 8.dp)
                            .align(Alignment.CenterHorizontally),
                        elevation = CardDefaults.elevatedCardElevation(2.dp),
                        shape = RoundedCornerShape(32.dp)
                    )  {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth().background(BackgroundGray).padding(horizontal = 6.dp, vertical = 2.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            TextField(
                                value = message,
                                onValueChange = {
                                    message = it
                                },
                                placeholder = { Text(text = stringResource(R.string.type_message_here), color = GrayFont, maxLines = 1, overflow = TextOverflow.Ellipsis) },
                                modifier = Modifier.weight(1f),
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                                colors = TextFieldDefaults.colors(
                                    focusedContainerColor = BackgroundGray,
                                    unfocusedContainerColor = BackgroundGray,
                                    disabledContainerColor = BackgroundGray,
                                    focusedIndicatorColor = Color.Transparent,
                                    unfocusedIndicatorColor = Color.Transparent,
                                    cursorColor = GrayFont,
                                    focusedTextColor = Color.Black,
                                    unfocusedTextColor = Color.Black,
                                )
                            )
                            IconButton(onClick = {
                                if (message.isNotBlank()){
                                    messagingViewModel.sendMessage(message = message.trim(), roomId = roomDto.id)
                                    message = ""
                                }
                            }) {
                                Icon(
                                    painter = painterResource(R.drawable.ic_send),
                                    contentDescription = "send icon",
                                    tint = Color.Unspecified
                                )
                            }
                        }
                    }
                }
            }

        }else{
            Column(
                modifier = modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                CircularProgressIndicator()
            }
        }
    }else{
        NotFound()
    }
}