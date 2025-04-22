package com.example.stageconnect.data.model

data class Offer(
    val company: String,
    val position: String,
    val logo: Int,
    val location: String,
    val salary: String,
    val postedDate: String,
    val options: List<String>,
    val jobDescription: String,
    val requirementSkills: List<String>,
    val education: String,
    val keySkills: List<String>,
    val jobSummary: JobSummary,
    val companyDescription: String,
    var isSaved: Boolean = false
)
