package com.example.stageconnect.domain.model

import androidx.room.Entity

@Entity(tableName = "certifications")
data class Certification(var id: Long? = null,
                         var title: String,
                         var organization: String,
                         var dateOfIssue: String,
                         var expirationDate: String,
                         var credentialWillNotExpire: Boolean,
                         var credentialId: String,
                         var credentialUrl: String,
                         var userId: Long)
