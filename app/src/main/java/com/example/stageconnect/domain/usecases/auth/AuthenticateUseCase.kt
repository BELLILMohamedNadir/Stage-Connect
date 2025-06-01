package com.example.stageconnect.domain.usecases.auth

import com.example.stageconnect.data.dtos.AuthenticationRequest
import com.example.stageconnect.data.dtos.AuthenticationResponse
import com.example.stageconnect.data.remote.repository.AuthenticationRepository
import javax.inject.Inject


class AuthenticateUseCase @Inject constructor(
    private val authenticationRepository: AuthenticationRepository
) {

    suspend fun execute(request: AuthenticationRequest): AuthenticationResponse {
        return authenticationRepository.authenticate(request)
    }

}
