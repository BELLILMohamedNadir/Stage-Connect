package com.example.stageconnect.data.dtos

import java.time.LocalDate

data class LanguageDto(
    var id: Long? = null,
    var language: String,
    var proficiency: String,
    var userId: Long
)