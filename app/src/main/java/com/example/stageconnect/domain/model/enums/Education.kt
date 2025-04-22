package com.example.stageconnect.domain.model.enums

enum class Education(val label: String) {
    LESS_THAN_HIGH_SCHOOL("Less Than High School"),
    HIGH_SCHOOL("High School"),
    DIPLOMA("Diploma"),
    BACHELOR_DEGREE("Bachelor’s Degree"),
    MASTER_DEGREE("Master’s Degree"),
    DOCTORAL_OR_PROFESSIONAL_DEGREE("Doctoral or Professional Degree");

    override fun toString(): String = label
}