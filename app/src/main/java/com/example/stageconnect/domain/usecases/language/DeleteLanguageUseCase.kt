package com.example.stageconnect.domain.usecases.language

import com.example.stageconnect.data.remote.repository.LanguageRepository
import com.example.stageconnect.data.remote.repository.WorkExperienceRepository
import javax.inject.Inject


class DeleteLanguageUseCase @Inject constructor(
    private val languageRepository: LanguageRepository
) {
    suspend fun execute(id: Long) {
        languageRepository.deleteLanguage(id)
    }

}