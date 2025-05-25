package com.example.stageconnect.data.remote.repository

import com.example.stageconnect.data.dtos.AuthenticationRequest
import com.example.stageconnect.data.dtos.AuthenticationResponse
import com.example.stageconnect.data.dtos.EstablishmentsDto
import com.example.stageconnect.data.remote.api.ApiService
import javax.inject.Inject


// Repository Interface
interface EstablishmentRepository {
    suspend fun getAllEstablishment(): List<EstablishmentsDto>
}

// Repository Implementation
class EstablishmentRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : EstablishmentRepository {
    override suspend fun getAllEstablishment(): List<EstablishmentsDto> {
        return apiService.getAllEstablishment()
    }
}
