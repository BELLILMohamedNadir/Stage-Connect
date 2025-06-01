package com.example.stageconnect.data.remote.repository

import com.example.stageconnect.data.dtos.ApplicationDto
import com.example.stageconnect.data.remote.api.ApiService
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import javax.inject.Inject


// Repository Interface
interface ApplicationRepository {
    suspend fun createApplication(applicationDto: String, file: File): ApplicationDto
    suspend fun getStudentApplications(id: Long): List<ApplicationDto>
    suspend fun getRecruiterApplications(id: Long): List<ApplicationDto>
    suspend fun getEstablishmentApplications(id: Long): List<ApplicationDto>
    suspend fun deleteApplication(applicationId: Long, studentId: Long)
    suspend fun updateApplication(applicationId: Long, applicationDto: ApplicationDto) : ApplicationDto
}

// Repository Implementation
class ApplicationRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : ApplicationRepository {

    override suspend fun createApplication(applicationDto: String, file: File): ApplicationDto {
        val userDtoRequestBody = applicationDto.toRequestBody("text/plain".toMediaTypeOrNull())

        val pdfPart = file.let {
            val requestBody = it.asRequestBody("application/pdf".toMediaTypeOrNull())
            MultipartBody.Part.createFormData("file", it.name, requestBody)
        }

        return apiService.createApplication(userDtoRequestBody, pdfPart)
    }

    override suspend fun getStudentApplications(id: Long): List<ApplicationDto> {
        return apiService.getStudentApplications(id)
    }

    override suspend fun getRecruiterApplications(id: Long): List<ApplicationDto> {
        return apiService.getRecruiterApplications(id)
    }

    override suspend fun getEstablishmentApplications(id: Long): List<ApplicationDto> {
        return apiService.getEstablishmentApplications(id)
    }

    override suspend fun deleteApplication(applicationId: Long, studentId: Long) {
        apiService.deleteApplication(applicationId, studentId)
    }

    override suspend fun updateApplication(applicationId: Long, applicationDto: ApplicationDto): ApplicationDto {
        return apiService.updateApplication(applicationId, applicationDto)
    }
}
