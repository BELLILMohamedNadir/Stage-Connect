package com.example.stageconnect.domain.model

import androidx.room.Entity
import java.util.UUID

@Entity(tableName = "rooms")
data class Room(
    val id: UUID,
    val senderId: Long,
    val sender: String,
    val logo: String,
    val lastMessage: String,
    val lastMessageTime: String?,
)