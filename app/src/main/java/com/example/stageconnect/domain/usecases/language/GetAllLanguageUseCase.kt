package com.example.stageconnect.domain.usecases.language

import com.example.stageconnect.data.dtos.LanguageDto
import com.example.stageconnect.data.remote.repository.LanguageRepository
import javax.inject.Inject


class GetAllLanguageUseCase @Inject constructor(
    private val languageRepository: LanguageRepository
) {
    suspend fun execute(id: Long): List<LanguageDto> {
      return languageRepository.getAllLanguages(id)
    }

}