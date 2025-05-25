package com.example.stageconnect.data.dtos

data class InternshipDto(
    var id: Long? = null,
    var title: String,
    var organization: String,
    var role: String,
    var startDate: String,
    var endDate: String,
    var current: Boolean,
    var description: String,
    var organizationWebsite: String,
    var userId: Long
)