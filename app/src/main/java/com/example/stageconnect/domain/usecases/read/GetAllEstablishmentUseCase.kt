package com.example.stageconnect.domain.usecases.read

import com.example.stageconnect.data.dtos.EstablishmentsDto
import com.example.stageconnect.data.remote.repository.EstablishmentRepository
import javax.inject.Inject


class GetAllEstablishmentUseCase @Inject constructor(
    private val establishmentRepository: EstablishmentRepository
) {

    suspend fun execute(): List<EstablishmentsDto> {
        return establishmentRepository.getAllEstablishment()
    }

}
