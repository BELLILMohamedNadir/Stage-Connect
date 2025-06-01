package com.example.stageconnect.domain.model

import androidx.room.Entity

@Entity(tableName = "educations")
data class Education(var id: Long? = null,
                     var education: String,
                     var course: String,
                     var university: String,
                     var startDate: String,
                     var endDate: String,
                     var graduated: Boolean,
                     var gpa: Float,
                     var total: Float,
                     var description: String,
                     var userId: Long)
