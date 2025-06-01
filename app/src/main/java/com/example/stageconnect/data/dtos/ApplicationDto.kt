package com.example.stageconnect.data.dtos

import androidx.room.Entity
import com.example.stageconnect.domain.model.enums.STATUS
import java.util.UUID

data class ApplicationDto(
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