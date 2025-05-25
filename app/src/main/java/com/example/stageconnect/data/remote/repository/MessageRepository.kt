package com.example.stageconnect.data.remote.repository

import com.example.stageconnect.data.dtos.MessageDto
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


// Repository Interface
interface MessageRepository {
    fun connect(roomId: Long)
    fun disconnect()
    fun sendMessage(message: MessageDto)
    fun observeMessages(): Flow<MessageDto>
}
