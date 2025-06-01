package com.example.stageconnect.domain.usecases.offer

import com.example.stageconnect.data.dtos.OfferDto
import com.example.stageconnect.data.remote.repository.OfferRepository
import javax.inject.Inject


class UpdateOfferUseCase @Inject constructor(
    private val offerRepository: OfferRepository
) {
    suspend fun execute(id: Long, offerDto: OfferDto): OfferDto {
        return offerRepository.updateOffer(id, offerDto)
    }
}
