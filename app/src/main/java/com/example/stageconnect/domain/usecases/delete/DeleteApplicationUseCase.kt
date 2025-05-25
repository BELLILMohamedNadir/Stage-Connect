package com.example.stageconnect.domain.usecases.delete

import com.example.stageconnect.data.dtos.EstablishmentsDto
import com.example.stageconnect.data.remote.repository.ApplicationRepository
import com.example.stageconnect.data.remote.repository.EstablishmentRepository
import javax.inject.Inject


class DeleteApplicationUseCase @Inject constructor(
    private val applicationRepository: ApplicationRepository
) {

    suspend fun execute(applicationId: Long, studentId: Long) {
        return applicationRepository.deleteApplication(applicationId, studentId)
    }

}
