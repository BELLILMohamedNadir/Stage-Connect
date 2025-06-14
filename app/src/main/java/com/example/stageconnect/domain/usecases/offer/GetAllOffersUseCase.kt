package com.example.stageconnect.domain.usecases.offer

import com.example.stageconnect.data.dtos.OfferDto
import com.example.stageconnect.data.remote.repository.OfferRepository
import javax.inject.Inject


class GetAllOffersUseCase @Inject constructor(
    private val offerRepository: OfferRepository
) {
    suspend fun execute(studentId: Long): List<OfferDto> {
        return offerRepository.getAllOffer(studentId)
    }
}
