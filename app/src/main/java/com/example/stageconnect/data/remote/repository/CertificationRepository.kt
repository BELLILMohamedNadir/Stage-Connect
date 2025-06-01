package com.example.stageconnect.data.remote.repository

import com.example.stageconnect.data.dtos.CertificationDto
import com.example.stageconnect.data.dtos.WorkExperienceDto
import com.example.stageconnect.data.remote.api.ApiService
import javax.inject.Inject


interface CertificationRepository {
    suspend fun createCertification(certificationDto: CertificationDto): CertificationDto
    suspend fun getAllCertification(id: Long): List<CertificationDto>
    suspend fun updateCertification(id: Long, certificationDto: CertificationDto): CertificationDto
    suspend fun deleteCertification(id: Long)
}

class CertificationRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : CertificationRepository {
    override suspend fun createCertification(certificationDto: CertificationDto): CertificationDto {
        return apiService.createCertification(certificationDto)
    }

    override suspend fun getAllCertification(id: Long): List<CertificationDto> {
        return apiService.getCertifications(id)
    }

    override suspend fun updateCertification(
        id: Long,
        certificationDto: CertificationDto
    ): CertificationDto {
        return apiService.updateCertification(id, certificationDto)
    }

    override suspend fun deleteCertification(id: Long) {
        apiService.deleteCertification(id)
    }

}
