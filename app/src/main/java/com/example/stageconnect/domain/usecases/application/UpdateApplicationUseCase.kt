package com.example.stageconnect.domain.usecases.application

import com.example.stageconnect.data.dtos.ApplicationDto
import com.example.stageconnect.data.remote.repository.ApplicationRepository
import javax.inject.Inject


class UpdateApplicationUseCase @Inject constructor(
    private val applicationRepository: ApplicationRepository
) {

    suspend fun execute(applicationId: Long, applicationDto: ApplicationDto) : ApplicationDto {
        return applicationRepository.updateApplication(applicationId, applicationDto)
    }

}
