package com.example.stageconnect.domain.model.enums

enum class JobLevel(val label: String) {
    INTERNSHIP("Internship"),
    ENTRY_LEVEL_JUNIOR_APPRENTICE("Entry Level / Junior, Apprentice"),
    ASSOCIATE_SUPERVISOR("Associate / Supervisor"),
    MID_SENIOR_LEVEL("Mid- Senior Level"),
    DIRECTOR_EXECUTIVE("Director / Executive");

    override fun toString(): String = label
}