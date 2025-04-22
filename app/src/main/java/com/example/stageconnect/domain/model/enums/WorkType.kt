package com.example.stageconnect.domain.model.enums

enum class WorkType(val label: String) {
    ON_SITE("Onsite (Work From Office)"),
    REMOTE("Remote (Work From Home)");

    override fun toString(): String = label
}