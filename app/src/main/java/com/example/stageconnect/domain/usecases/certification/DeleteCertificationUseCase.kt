package com.example.stageconnect.domain.usecases.certification

import com.example.stageconnect.data.remote.repository.CertificationRepository
import com.example.stageconnect.data.remote.repository.WorkExperienceRepository
import javax.inject.Inject


class DeleteCertificationUseCase @Inject constructor(
    private val certificationRepository: CertificationRepository
) {
    suspend fun execute(id: Long) {
        certificationRepository.deleteCertification(id)
    }

}