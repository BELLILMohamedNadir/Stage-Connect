package com.example.stageconnect.data.dtos

import java.util.UUID

data class RoomDto(
    val id: UUID,
    val senderId: Long,
    val sender: String,
    val logo: String,
    val lastMessage: String,
    val lastMessageTime: String?,
)