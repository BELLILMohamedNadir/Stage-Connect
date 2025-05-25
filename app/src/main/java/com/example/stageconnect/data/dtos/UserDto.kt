package com.example.stageconnect.data.dtos

import com.example.stageconnect.domain.model.enums.ROLE


data class UserDto(
    var id: Long? = null,
    var name: String? = null,
    var firstName: String? = null,
    var dateOfBirth: String? = null,
    var email: String? = null,
    var password: String? = null,
    var role: ROLE? = null,
    var phone: String? = null,
    var photo: String? = null,
    var resume: String? = null,
    var gender: String? = null,
    var address: String? = null,
    var establishmentId: Long? = null,
    var summary: String? = null,
    var currentPosition: String? = null,
    var organizationName: String? = null,
    var skills: List<String>? = null,
    var expertises: List<String>? = null
)