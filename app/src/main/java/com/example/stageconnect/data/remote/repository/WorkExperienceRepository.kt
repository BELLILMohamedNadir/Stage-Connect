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
    suspend fun getAllWorkExperiences(id: Long): List<WorkExperienceDto>
    suspend fun updateWorkExperience(id: Long, workExperienceDto: WorkExperienceDto): WorkExperienceDto
    suspend fun deleteWorkExperience(id: Long)
}

// Repository Implementation
class WorkExperienceRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : WorkExperienceRepository {
    override suspend fun createWorkExperience(workExperienceDto: WorkExperienceDto): WorkExperienceDto {
        return apiService.createWorkExperience(workExperienceDto)
    }

    override suspend fun getAllWorkExperiences(id: Long): List<WorkExperienceDto> {
        return apiService.getWorkExperiences(id)
    }

    override suspend fun updateWorkExperience(
        id: Long,
        workExperienceDto: WorkExperienceDto
    ): WorkExperienceDto {
        return apiService.updateWorkExperience(id, workExperienceDto)
    }

    override suspend fun deleteWorkExperience(id: Long) {
        apiService.deleteWorkExperience(id)
    }

}
