package com.example.stageconnect.domain.model

data class Offer(
    val id: Long? = null,
    val position: String,
    val website: String?,
    val location: String,
    val salaryStart: Long,
    val salaryEnd: Long,
    val salaryUnit: String,
    val postedDate: String? = null,
    val options: List<String>,
    val experience: String,
    val keySkills: List<String>,
    val requirementSkills: String,
    val jobDescription: String,
    val companyDescription: String?,
    val workType: String,
    val jobLevels: List<String>,
    val employmentTypes: List<String>,
    val education: List<String>,
    val jobFunction: List<String>,
    var isSaved: Boolean = false,
    var isApplied: Boolean = false,
    var recruiterId: Long,
    val logo: String? = null,
    val company: String? = null
)