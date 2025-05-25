package com.example.stageconnect.data.dtos

data class MessageDto(
    val id: Long? = null,
    val content: String,
    val timestamp: String? = null,
    val senderId: Long)