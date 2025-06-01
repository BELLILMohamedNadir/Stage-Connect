package com.example.stageconnect.data.dtos

data class AuthenticationRequest(
    val email: String,
    val password: String,
    var deviceToken: String? = null
)
