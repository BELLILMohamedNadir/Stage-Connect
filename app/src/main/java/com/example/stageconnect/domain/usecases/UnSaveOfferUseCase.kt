package com.example.stageconnect.domain.usecases

import android.content.Context
import android.net.Uri
import com.example.stageconnect.data.dtos.AuthenticationRequest
import com.example.stageconnect.data.dtos.AuthenticationResponse
import com.example.stageconnect.data.dtos.UserDto
import com.example.stageconnect.data.remote.repository.AuthenticationRepository
import com.example.stageconnect.data.remote.repository.OfferRepository
import com.example.stageconnect.data.remote.repository.RegisterRepository
import com.fasterxml.jackson.databind.ObjectMapper
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.File
import java.io.InputStream
import javax.inject.Inject


class UnSaveOfferUseCase @Inject constructor(
    private val offerRepository: OfferRepository
) {

    suspend fun execute(offerId: Long, studentId: Long) {
        offerRepository.unSaveOffer(offerId, studentId)
    }
}
