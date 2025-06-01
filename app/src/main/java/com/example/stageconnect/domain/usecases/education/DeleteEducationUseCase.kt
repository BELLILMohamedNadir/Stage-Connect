package com.example.stageconnect.domain.usecases.education

import com.example.stageconnect.data.remote.repository.EducationRepository
import com.example.stageconnect.data.remote.repository.WorkExperienceRepository
import javax.inject.Inject


class DeleteEducationUseCase @Inject constructor(
    private val educationRepository: EducationRepository
) {
    suspend fun execute(id: Long) {
        educationRepository.deleteEducation(id)
    }

}