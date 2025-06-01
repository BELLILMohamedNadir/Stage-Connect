package com.example.stageconnect.domain.usecases.messaging

import com.example.stageconnect.data.dtos.MessageDto
import com.example.stageconnect.data.remote.repository.RoomRepository
import java.util.UUID
import javax.inject.Inject


class GetAllMessagesUseCase @Inject constructor(
    private val roomRepository: RoomRepository
) {
    suspend fun execute(roomId: UUID): List<MessageDto> {
        return roomRepository.getMessages(roomId)
    }
}
