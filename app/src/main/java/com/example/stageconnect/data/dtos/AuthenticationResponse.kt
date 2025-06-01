package com.example.stageconnect.data.dtos

import com.example.stageconnect.domain.model.enums.ROLE


data class AuthenticationResponse(
    val id: Long,
    val name: String,
    val firstName: String?,
    val dateOfBirth: String?,
    val address: String,
    val email: String,
    val role: String,
    val phone: String,
    val resume: String,
    val gender: String?,
    val summary: String?,
    val organizationName: String?,
    val photo: String,
    val currentPosition: String?,
    val skills: List<String>?,
    val token: String,
)
