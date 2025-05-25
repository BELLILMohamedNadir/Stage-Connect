package com.example.stageconnect.data.remote.repository

import com.example.stageconnect.data.dtos.CertificationDto
import com.example.stageconnect.data.dtos.EducationDto
import com.example.stageconnect.data.remote.api.ApiService
import javax.inject.Inject


// Repository Interface
interface EducationRepository {
    suspend fun createEducation(educationDto: EducationDto): EducationDto
}

// Repository Implementation
class EducationRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : EducationRepository {
    override suspend fun createEducation(educationDto: EducationDto): EducationDto {
        return apiService.createEducation(educationDto)
    }
}
