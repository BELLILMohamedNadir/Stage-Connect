package com.example.stageconnect.domain.usecases.read

import com.example.stageconnect.data.dtos.ApplicationDto
import com.example.stageconnect.data.dtos.EstablishmentsDto
import com.example.stageconnect.data.remote.repository.ApplicationRepository
import com.example.stageconnect.data.remote.repository.EstablishmentRepository
import com.example.stageconnect.domain.model.Application
import javax.inject.Inject


class GetEstablishmentApplicationsUseCase @Inject constructor(
    private val applicationRepository: ApplicationRepository
) {

    suspend fun execute(id: Long): List<ApplicationDto> {
        return applicationRepository.getEstablishmentApplications(id)
    }

}
