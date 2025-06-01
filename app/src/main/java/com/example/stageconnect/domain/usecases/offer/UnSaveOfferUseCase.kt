package com.example.stageconnect.domain.usecases.offer

import com.example.stageconnect.data.remote.repository.OfferRepository
import javax.inject.Inject


class UnSaveOfferUseCase @Inject constructor(
    private val offerRepository: OfferRepository
) {

    suspend fun execute(offerId: Long, studentId: Long) {
        offerRepository.unSaveOffer(offerId, studentId)
    }
}
