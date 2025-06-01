package com.example.stageconnect.data.remote.repository

import com.example.stageconnect.data.dtos.AuthenticationRequest
import com.example.stageconnect.data.dtos.AuthenticationResponse
import com.example.stageconnect.data.dtos.EstablishmentDto
import com.example.stageconnect.data.dtos.EstablishmentsDto
import com.example.stageconnect.data.dtos.RecruiterDto
import com.example.stageconnect.data.dtos.StudentDto
import com.example.stageconnect.data.remote.api.ApiService
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import javax.inject.Inject


// Repository Interface
interface EstablishmentRepository {
    suspend fun getAllEstablishment(): List<EstablishmentsDto>
    suspend fun getAllEstablishmentStudents(id: Long): List<StudentDto>
    suspend fun updateEstablishment(establishmentDto: String, file: File): EstablishmentDto
}

// Repository Implementation
class EstablishmentRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : EstablishmentRepository {
    override suspend fun getAllEstablishment(): List<EstablishmentsDto> {
        return apiService.getAllEstablishment()
    }

    override suspend fun getAllEstablishmentStudents(id: Long): List<StudentDto> {
        return apiService.getEstablishmentStudents(id)
    }

    override suspend fun updateEstablishment(establishmentDto: String, file: File): EstablishmentDto {
        val establishmentDtoRequestBody = establishmentDto.toRequestBody("text/plain".toMediaTypeOrNull())

        val fileUri = file.let {
            val requestBody = it.asRequestBody("image/*".toMediaTypeOrNull())
            MultipartBody.Part.createFormData("file", it.name, requestBody)
        }
        return apiService.updateEstablishment(establishmentDtoJson = establishmentDtoRequestBody, file = fileUri)
    }
}
