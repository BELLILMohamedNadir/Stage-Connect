package com.example.stageconnect.data.remote.repository

import com.example.stageconnect.data.dtos.AuthenticationRequest
import com.example.stageconnect.data.dtos.AuthenticationResponse
import com.example.stageconnect.data.remote.api.ApiService
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import javax.inject.Inject


// Repository Interface
interface AuthenticationRepository {
    suspend fun authenticate(request: AuthenticationRequest): AuthenticationResponse
}

// Repository Implementation
class AuthenticationRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : AuthenticationRepository {
    override suspend fun authenticate(request: AuthenticationRequest): AuthenticationResponse {
        return apiService.authenticate(request)
    }


}
