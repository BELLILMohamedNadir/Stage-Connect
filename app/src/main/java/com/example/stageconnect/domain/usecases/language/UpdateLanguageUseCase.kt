package com.example.stageconnect.domain.usecases.language

import com.example.stageconnect.data.dtos.LanguageDto
import com.example.stageconnect.data.dtos.WorkExperienceDto
import com.example.stageconnect.data.remote.repository.LanguageRepository
import com.example.stageconnect.data.remote.repository.WorkExperienceRepository
import javax.inject.Inject


class UpdateLanguageUseCase @Inject constructor(
    private val languageRepository: LanguageRepository
) {
    suspend fun execute(id: Long, languageDto: LanguageDto): LanguageDto {
      return languageRepository.updateLanguage(id, languageDto)
    }

}