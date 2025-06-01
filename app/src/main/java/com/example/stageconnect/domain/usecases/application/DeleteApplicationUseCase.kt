package com.example.stageconnect.domain.usecases.application

import com.example.stageconnect.data.remote.repository.ApplicationRepository
import javax.inject.Inject


class DeleteApplicationUseCase @Inject constructor(
    private val applicationRepository: ApplicationRepository
) {

    suspend fun execute(applicationId: Long, studentId: Long) {
        return applicationRepository.deleteApplication(applicationId, studentId)
    }

}
