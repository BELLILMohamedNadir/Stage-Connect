package com.example.stageconnect.domain.usecases

import android.content.Context
import android.net.Uri
import com.example.stageconnect.data.dtos.AuthenticationRequest
import com.example.stageconnect.data.dtos.AuthenticationResponse
import com.example.stageconnect.data.dtos.UserDto
import com.example.stageconnect.data.remote.repository.AuthenticationRepository
import com.example.stageconnect.data.remote.repository.RegisterRepository
import com.fasterxml.jackson.databind.ObjectMapper
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.File
import java.io.InputStream
import javax.inject.Inject


class AuthenticateUseCase @Inject constructor(
    private val authenticationRepository: AuthenticationRepository
) {

    suspend fun execute(request: AuthenticationRequest): AuthenticationResponse {
        return authenticationRepository.authenticate(request)
    }

}
