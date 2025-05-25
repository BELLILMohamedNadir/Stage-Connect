package com.example.stageconnect.domain.model

data class StompFrame(
    val command: String,
    val headers: Map<String, String>,
    val body: String
)