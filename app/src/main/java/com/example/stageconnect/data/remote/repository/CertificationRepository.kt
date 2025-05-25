package com.example.stageconnect.data.remote.repository

import com.example.stageconnect.data.dtos.CertificationDto
import com.example.stageconnect.data.remote.api.ApiService
import javax.inject.Inject


interface CertificationRepository {
    suspend fun createCertification(certificationDto: CertificationDto): CertificationDto
}

class CertificationRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : CertificationRepository {
    override suspend fun createCertification(certificationDto: CertificationDto): CertificationDto {
        return apiService.createCertification(certificationDto)
    }

}
