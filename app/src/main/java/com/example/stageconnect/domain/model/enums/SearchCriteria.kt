package com.example.stageconnect.domain.model.enums

enum class SearchCriteria(val label: String) {
    LOCATION_SALARY("Location & Salary"),
    WORK_TYPE("Work Type"),
    JOB_LEVEL("Job Level"),
    EMPLOYMENT_TYPE("Employment Type"),
    EXPERIENCE("Experience"),
    EDUCATION("Education"),
    JOB_FUNCTION("Job Function");

    override fun toString(): String = label
}