package com.example.stageconnect.data.dtos

data class ProjectDto(
    var id: Long? = null,
    var projectName: String,
    var role: String,
    var startDate: String,
    var endDate: String,
    var current: Boolean,
    var description: String,
    var projectUrl: String,
    var userId: Long
)