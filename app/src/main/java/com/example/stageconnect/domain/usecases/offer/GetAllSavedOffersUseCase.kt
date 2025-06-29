package com.example.stageconnect.domain.usecases.offer

import com.example.stageconnect.data.dtos.OfferDto
import com.example.stageconnect.data.remote.repository.OfferRepository
import javax.inject.Inject


class GetAllSavedOffersUseCase @Inject constructor(
    private val offerRepository: OfferRepository
) {
    suspend fun execute(studentId: Long): List<OfferDto> {
        return offerRepository.getAllSavedOffer(studentId)
    }
}
