package com.example.stageconnect.domain.model

import androidx.room.Entity

@Entity(tableName = "work-experiences")
data class WorkExperience (var id: Long? = null,
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
                           var userId: Long)