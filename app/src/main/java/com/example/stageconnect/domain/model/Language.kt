package com.example.stageconnect.domain.model

import androidx.room.Entity

@Entity(tableName = "languages")
data class Language(var id: Long? = null,
                    var language: String,
                    var proficiency: String,
                    var userId: Long)
