package com.example.stageconnect.data.remote.repository

import com.example.stageconnect.data.dtos.CertificationDto
import com.example.stageconnect.data.dtos.EducationDto
import com.example.stageconnect.data.dtos.InternshipDto
import com.example.stageconnect.data.dtos.LanguageDto
import com.example.stageconnect.data.remote.api.ApiService
import javax.inject.Inject


// Repository Interface
interface LanguageRepository {
    suspend fun createLanguage(languageDto: LanguageDto): LanguageDto
    suspend fun getAllLanguages(id: Long): List<LanguageDto>
    suspend fun updateLanguage(id: Long, languageDto: LanguageDto): LanguageDto
    suspend fun deleteLanguage(id: Long)
}

// Repository Implementation
class LanguageRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : LanguageRepository {
    override suspend fun createLanguage(languageDto: LanguageDto): LanguageDto {
        return apiService.createLanguage(languageDto)
    }

    override suspend fun getAllLanguages(id: Long): List<LanguageDto> {
        return apiService.getLanguages(id)
    }

    override suspend fun updateLanguage(id: Long, languageDto: LanguageDto): LanguageDto {
        return apiService.updateLanguage(id, languageDto)
    }

    override suspend fun deleteLanguage(id: Long) {
        apiService.deleteLanguage(id)
    }
}
