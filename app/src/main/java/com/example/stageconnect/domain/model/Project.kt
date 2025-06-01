package com.example.stageconnect.domain.model

import androidx.room.Entity

@Entity(tableName = "projects")
data class Project(var id: Long? = null,
                   var projectName: String,
                   var role: String,
                   var startDate: String,
                   var endDate: String,
                   var current: Boolean,
                   var description: String,
                   var projectUrl: String,
                   var userId: Long)
