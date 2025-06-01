package com.example.stageconnect.domain.model

import androidx.room.Entity
import com.example.stageconnect.data.dtos.CertificationDto
import com.example.stageconnect.data.dtos.EducationDto
import com.example.stageconnect.data.dtos.OfferDto
import com.example.stageconnect.data.dtos.ProjectDto
import com.example.stageconnect.data.dtos.RoomDto
import com.example.stageconnect.data.dtos.WorkExperienceDto
import com.example.stageconnect.domain.model.enums.STATUS

@Entity(tableName = "applications")
data class Application(
    var id: Long? = null,
    var date: String? = null,
    var recruiterStatus: STATUS = STATUS.SENT,
    var establishmentStatus: STATUS = STATUS.SENT,
    var status: STATUS = STATUS.SENT,
    var offerId: Long,
    var refused: Boolean = false,
    var studentId: Long,
    var studentName: String = "",
    var refusedReason: String = "",
    var logoUrl: String? = null,
    var resumeUrl: String? = null,
    var conventionUrl: String? = null,
    var summary: String? = null,
    var companyName: String? = null,
    var companySummary: String? = null,
    var companyAddress: String? = null,
    var roomDto: RoomDto? = null,
    var educations: List<EducationDto>? = null,
    var workExperiences: List<WorkExperienceDto>? = null,
    var certifications: List<CertificationDto>? = null,
    var projects: List<ProjectDto>? = null,
    var offerDto: OfferDto? = null,
    var coverLetter: String
)
