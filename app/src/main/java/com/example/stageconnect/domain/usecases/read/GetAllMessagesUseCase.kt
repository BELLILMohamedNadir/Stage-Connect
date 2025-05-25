package com.example.stageconnect.domain.usecases.read

import com.example.stageconnect.data.dtos.EstablishmentsDto
import com.example.stageconnect.data.dtos.MessageDto
import com.example.stageconnect.data.dtos.OfferDto
import com.example.stageconnect.data.remote.repository.EstablishmentRepository
import com.example.stageconnect.data.remote.repository.OfferRepository
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
