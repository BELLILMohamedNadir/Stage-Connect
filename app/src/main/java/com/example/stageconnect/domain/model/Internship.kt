package com.example.stageconnect.domain.model

import androidx.room.Entity

@Entity(tableName = "internships")
data class Internship(var id: Long? = null,
                      var title: String,
                      var organization: String,
                      var role: String,
                      var startDate: String,
                      var endDate: String,
                      var current: Boolean,
                      var description: String,
                      var organizationWebsite: String,
                      var userId: Long)
