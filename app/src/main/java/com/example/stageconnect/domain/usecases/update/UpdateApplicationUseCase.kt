package com.example.stageconnect.domain.usecases.update

import com.example.stageconnect.data.dtos.ApplicationDto
import com.example.stageconnect.data.dtos.EstablishmentsDto
import com.example.stageconnect.data.remote.repository.ApplicationRepository
import com.example.stageconnect.data.remote.repository.EstablishmentRepository
import javax.inject.Inject


class UpdateApplicationUseCase @Inject constructor(
    private val applicationRepository: ApplicationRepository
) {

    suspend fun execute(applicationId: Long, applicationDto: ApplicationDto) : ApplicationDto {
        return applicationRepository.updateApplication(applicationId, applicationDto)
    }

}
