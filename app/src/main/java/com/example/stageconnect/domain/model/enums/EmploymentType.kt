package com.example.stageconnect.domain.model.enums

enum class EmploymentType(val label: String) {
    FULL_TIME("Full Time"),
    PART_TIME("Part Time"),
    FREELANCE("Freelance"),
    CONTRACTUAL("Contractual");

    override fun toString(): String = label
}