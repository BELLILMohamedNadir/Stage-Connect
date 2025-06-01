package com.example.stageconnect.domain.usecases.language

import com.example.stageconnect.data.dtos.LanguageDto
import com.example.stageconnect.data.remote.repository.LanguageRepository
import javax.inject.Inject


class CreateLanguageUseCase @Inject constructor(
    private val languageRepository: LanguageRepository
) {
    suspend fun execute(languageDto: LanguageDto): LanguageDto {
        return languageRepository.createLanguage(languageDto)
    }
}
