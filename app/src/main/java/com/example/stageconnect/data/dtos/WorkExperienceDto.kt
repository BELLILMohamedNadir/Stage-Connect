package com.example.stageconnect.data.dtos

data class WorkExperienceDto(
    var id: Long? = null,
    var jobTitle: String,
    var company: String,
    var startDate: String,
    var endDate: String,
    var currentWorkHere: Boolean,
    var description: String,
    var employmentType: String,
    var location: String,
    var jobLevel: String,
    var jobFunction: String,
    var userId: Long
)