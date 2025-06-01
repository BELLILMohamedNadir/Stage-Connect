package com.example.stageconnect.domain.usecases.workexperience

import com.example.stageconnect.data.dtos.WorkExperienceDto
import com.example.stageconnect.data.remote.repository.WorkExperienceRepository
import javax.inject.Inject


class CreateWorkExperienceUseCase @Inject constructor(
    private val workExperienceRepository: WorkExperienceRepository
) {
    suspend fun execute(workExperienceDto: WorkExperienceDto): WorkExperienceDto {
        return workExperienceRepository.createWorkExperience(workExperienceDto)
    }
}
