package com.example.stageconnect.domain.model.enums

enum class Experience(val label: String) {
    NO_EXPERIENCE("No Experience"),
    YEARS_1_5("1 - 5 Years"),
    YEARS_6_10("6 - 10 Years"),
    MORE_THAN_10_YEARS("More than 10 Years");

    override fun toString(): String = label
}