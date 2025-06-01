package com.example.stageconnect.domain.usecases.application

import com.example.stageconnect.data.dtos.ApplicationDto
import com.example.stageconnect.data.remote.repository.ApplicationRepository
import javax.inject.Inject


class GetEstablishmentApplicationsUseCase @Inject constructor(
    private val applicationRepository: ApplicationRepository
) {

    suspend fun execute(id: Long): List<ApplicationDto> {
        return applicationRepository.getEstablishmentApplications(id)
    }

}
