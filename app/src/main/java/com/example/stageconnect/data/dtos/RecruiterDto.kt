package com.example.stageconnect.data.dtos


data class RecruiterDto(
    var id: Long? = null,
    var name: String? = null,
    var firstName: String? = null,
    var dateOfBirth: String? = null,
    var email: String? = null,
    var phone: String? = null,
    var photo: String? = null,
    var address: String? = null,
    var summary: String? = null,
    var currentPosition: String? = null,
    var organizationName: String? = null,
)