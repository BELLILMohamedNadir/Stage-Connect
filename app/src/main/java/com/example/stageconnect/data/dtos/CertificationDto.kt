package com.example.stageconnect.data.dtos

import java.time.LocalDate

data class CertificationDto(
    var id: Long? = null,
    var title: String,
    var organization: String,
    var dateOfIssue: String,
    var expirationDate: String,
    var credentialWillNotExpire: Boolean,
    var credentialId: String,
    var credentialUrl: String,
    var userId: Long
)