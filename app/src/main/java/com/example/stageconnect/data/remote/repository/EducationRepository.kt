package com.example.stageconnect.data.remote.repository

import com.example.stageconnect.data.dtos.CertificationDto
import com.example.stageconnect.data.dtos.EducationDto
import com.example.stageconnect.data.remote.api.ApiService
import javax.inject.Inject


// Repository Interface
interface EducationRepository {
    suspend fun createEducation(educationDto: EducationDto): EducationDto
    suspend fun getAllEducation(id: Long): List<EducationDto>
    suspend fun updateEducation(id: Long, educationDto: EducationDto): EducationDto
    suspend fun deleteEducation(id: Long)
}

// Repository Implementation
class EducationRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : EducationRepository {
    override suspend fun createEducation(educationDto: EducationDto): EducationDto {
        return apiService.createEducation(educationDto)
    }

    override suspend fun getAllEducation(id: Long): List<EducationDto> {
        return apiService.getEducations(id)
    }

    override suspend fun updateEducation(id: Long, educationDto: EducationDto): EducationDto {
        return apiService.updateEducation(id, educationDto)
    }

    override suspend fun deleteEducation(id: Long) {
        apiService.deleteEducation(id)
    }
}
