package com.example.stageconnect.domain.usecases.certification

import com.example.stageconnect.data.dtos.CertificationDto
import com.example.stageconnect.data.remote.repository.CertificationRepository
import javax.inject.Inject


class CreateCertificationUseCase @Inject constructor(
    private val certificationRepository: CertificationRepository
) {
    suspend fun execute(certificationDto: CertificationDto): CertificationDto {
        return certificationRepository.createCertification(certificationDto)
    }
}
