package com.example.stageconnect.data.remote.repository

import com.example.stageconnect.data.dtos.CertificationDto
import com.example.stageconnect.data.dtos.EducationDto
import com.example.stageconnect.data.dtos.InternshipDto
import com.example.stageconnect.data.remote.api.ApiService
import javax.inject.Inject


// Repository Interface
interface InternshipRepository {
    suspend fun createInternship(internshipDto: InternshipDto): InternshipDto
}

// Repository Implementation
class InternshipRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : InternshipRepository {
    override suspend fun createInternship(internshipDto: InternshipDto): InternshipDto {
        return apiService.createInternship(internshipDto)
    }

}
