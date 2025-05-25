package com.example.stageconnect.domain.usecases.create

import com.example.stageconnect.data.dtos.ApplicationDto
import com.example.stageconnect.data.dtos.EducationDto
import com.example.stageconnect.data.dtos.InternshipDto
import com.example.stageconnect.data.dtos.LanguageDto
import com.example.stageconnect.data.remote.repository.ApplicationRepository
import com.example.stageconnect.data.remote.repository.EducationRepository
import com.example.stageconnect.data.remote.repository.InternshipRepository
import com.example.stageconnect.data.remote.repository.LanguageRepository
import javax.inject.Inject


class CreateLanguageUseCase @Inject constructor(
    private val languageRepository: LanguageRepository
) {
    suspend fun execute(languageDto: LanguageDto): LanguageDto {
        return languageRepository.createLanguage(languageDto)
    }
}
