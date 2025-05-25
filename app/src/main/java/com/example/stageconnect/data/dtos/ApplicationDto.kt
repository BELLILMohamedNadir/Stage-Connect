package com.example.stageconnect.data.dtos

import com.example.stageconnect.domain.model.enums.STATUS

data class ApplicationDto(
    var id: Long? = null,
    var date: String? = null,
    var recruiterStatus: STATUS = STATUS.SENT,
    var establishmentStatus: STATUS = STATUS.SENT,
    var status: STATUS = STATUS.SENT,
    var offerId: Long,
    var studentId: Long,
    var studentName: String,
    var logoUrl: String? = null,
    var resumeUrl: String? = null,
    var summary: String,
    var educations: List<EducationDto>,
    var workExperiences: List<WorkExperienceDto>,
    var certifications: List<CertificationDto>,
    var projects: List<ProjectDto>,
    var offerDto: OfferDto? = null,
    var coverLetter: String
)