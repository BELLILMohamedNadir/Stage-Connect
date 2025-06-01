package com.example.stageconnect.domain.usecases.workexperience

import com.example.stageconnect.data.dtos.WorkExperienceDto
import com.example.stageconnect.data.remote.repository.StudentRepository
import com.example.stageconnect.data.remote.repository.WorkExperienceRepository
import javax.inject.Inject


class UpdateWorkExperienceUseCase @Inject constructor(
    private val workExperienceRepository: WorkExperienceRepository
) {
    suspend fun execute(id: Long, workExperienceDto: WorkExperienceDto): WorkExperienceDto {
      return workExperienceRepository.updateWorkExperience(id, workExperienceDto)
    }

}