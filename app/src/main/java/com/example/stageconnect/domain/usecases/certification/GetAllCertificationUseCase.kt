package com.example.stageconnect.domain.usecases.certification

import com.example.stageconnect.data.dtos.CertificationDto
import com.example.stageconnect.data.dtos.WorkExperienceDto
import com.example.stageconnect.data.remote.repository.CertificationRepository
import com.example.stageconnect.data.remote.repository.WorkExperienceRepository
import javax.inject.Inject


class GetAllCertificationUseCase @Inject constructor(
    private val certificationRepository: CertificationRepository
) {
    suspend fun execute(id: Long): List<CertificationDto> {
      return certificationRepository.getAllCertification(id)
    }

}