package com.example.stageconnect.data.remote.repository

import com.example.stageconnect.data.dtos.CertificationDto
import com.example.stageconnect.data.dtos.EducationDto
import com.example.stageconnect.data.dtos.InternshipDto
import com.example.stageconnect.data.remote.api.ApiService
import javax.inject.Inject


// Repository Interface
interface InternshipRepository {
    suspend fun createInternship(internshipDto: InternshipDto): InternshipDto
    suspend fun getAllInternship(id: Long): List<InternshipDto>
    suspend fun updateInternship(id: Long, internshipDto: InternshipDto): InternshipDto
    suspend fun deleteInternship(id: Long)
}

// Repository Implementation
class InternshipRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : InternshipRepository {
    override suspend fun createInternship(internshipDto: InternshipDto): InternshipDto {
        return apiService.createInternship(internshipDto)
    }

    override suspend fun getAllInternship(id: Long): List<InternshipDto> {
        return apiService.getInternships(id)
    }

    override suspend fun updateInternship(id: Long, internshipDto: InternshipDto): InternshipDto {
        return apiService.updateInternship(id, internshipDto)
    }

    override suspend fun deleteInternship(id: Long) {
        apiService.deleteInternship(id)
    }

}
