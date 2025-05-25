package com.example.stageconnect.data.remote.repository

import com.example.stageconnect.data.dtos.MessageDto
import com.example.stageconnect.data.dtos.RoomDto
import com.example.stageconnect.data.remote.api.ApiService
import java.util.UUID
import javax.inject.Inject


// Repository Interface
interface RoomRepository {
    suspend fun getAllRooms(userId: Long): List<RoomDto>
    suspend fun getMessages(roomId: UUID): List<MessageDto>
}

// Repository Implementation
class RoomRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : RoomRepository {
    override suspend fun getAllRooms(userId: Long): List<RoomDto> {
        return apiService.getAllRooms(userId)
    }

    override suspend fun getMessages(roomId: UUID): List<MessageDto> {
        return apiService.getMessages(roomId)
    }
}
