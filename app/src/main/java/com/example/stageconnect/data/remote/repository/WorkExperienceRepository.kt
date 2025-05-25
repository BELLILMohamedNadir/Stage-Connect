package com.example.stageconnect.data.remote.repository

import com.example.stageconnect.data.dtos.CertificationDto
import com.example.stageconnect.data.dtos.EducationDto
import com.example.stageconnect.data.dtos.InternshipDto
import com.example.stageconnect.data.dtos.LanguageDto
import com.example.stageconnect.data.dtos.OfferDto
import com.example.stageconnect.data.dtos.ProjectDto
import com.example.stageconnect.data.dtos.WorkExperienceDto
import com.example.stageconnect.data.remote.api.ApiService
import javax.inject.Inject


// Repository Interface
interface WorkExperienceRepository {
    suspend fun createWorkExperience(workExperienceDto: WorkExperienceDto): WorkExperienceDto
}

// Repository Implementation
class WorkExperienceRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : WorkExperienceRepository {
    override suspend fun createWorkExperience(workExperienceDto: WorkExperienceDto): WorkExperienceDto {
        return apiService.createWorkExperience(workExperienceDto)
    }

}
