package com.example.stageconnect.domain.usecases.workexperience

import com.example.stageconnect.data.dtos.WorkExperienceDto
import com.example.stageconnect.data.remote.repository.StudentRepository
import com.example.stageconnect.data.remote.repository.WorkExperienceRepository
import javax.inject.Inject


class DeleteWorkExperienceUseCase @Inject constructor(
    private val workExperienceRepository: WorkExperienceRepository
) {
    suspend fun execute(id: Long) {
        workExperienceRepository.deleteWorkExperience(id)
    }

}