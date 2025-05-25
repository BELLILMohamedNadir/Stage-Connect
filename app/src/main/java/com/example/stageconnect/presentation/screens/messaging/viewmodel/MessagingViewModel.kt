package com.example.stageconnect.presentation.screens.messaging.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.stageconnect.data.dtos.MessageDto
import com.example.stageconnect.data.remote.api.StompWebSocketClient
import com.example.stageconnect.data.remote.repository.StorageRepository
import com.example.stageconnect.domain.CONSTANT.USER_ID
import com.example.stageconnect.domain.CONSTANT.USER_ROLE
import com.example.stageconnect.domain.model.enums.ROLE
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class MessagingViewModel @Inject constructor(
    private val storageRepository: StorageRepository,
    private val stompClient: StompWebSocketClient
) : ViewModel() {

    private val _messages = MutableStateFlow<List<MessageDto>>(emptyList())
    val messages: StateFlow<List<MessageDto>> get() = _messages

    init {
        viewModelScope.launch {
            stompClient.messages.collect { newMessages ->
                _messages.update { current ->
                    val existingIds = current.map { it.id }.toSet()
                    val uniqueNewMessages = newMessages.filterNot { it.id in existingIds }
                    current + uniqueNewMessages
                }
            }
        }
    }

    fun connect(roomId: UUID){
        stompClient.connect(roomId)
    }
    fun disconnect(){
        stompClient.disconnect()
    }

    fun sendMessage(message: String, roomId: UUID){
        val messageDto = MessageDto(
            content = message,
            senderId = storageRepository.get(USER_ID)!!.toLong()
        )
        stompClient.sendMessage(messageDto = messageDto, roomId = roomId)
    }

    fun addPastMessages(pastMessages: List<MessageDto?>) {
        _messages.update { current ->
            val validMessages = pastMessages.filterNotNull()
            validMessages + current
        }
    }

    fun clearData(){
        _messages.value = emptyList()
    }
}
