package com.example.stageconnect.data.dtos

import com.example.stageconnect.domain.model.enums.ROLE


data class AuthDto(
    var name: String? = null,
    var firstName: String? = null,
    var dateOfBirth: String? = null,
    var email: String? = null,
    var password: String? = null,
    var role: ROLE? = null,
    var phone: String? = null,
    var gender: String? = null,
    var establishmentId: Long? = null,
    var expertises: List<String>? = null
)