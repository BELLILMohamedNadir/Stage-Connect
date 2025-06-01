package com.example.stageconnect.domain.usecases.messaging

import com.example.stageconnect.data.dtos.RoomDto
import com.example.stageconnect.data.remote.repository.RoomRepository
import javax.inject.Inject


class GetAllRoomsUseCase @Inject constructor(
    private val roomRepository: RoomRepository
) {

    suspend fun execute(userId: Long): List<RoomDto> {
        return roomRepository.getAllRooms(userId)
    }

}
