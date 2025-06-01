package com.example.stageconnect.domain.usecases.workexperience

import com.example.stageconnect.data.dtos.WorkExperienceDto
import com.example.stageconnect.data.remote.repository.StudentRepository
import com.example.stageconnect.data.remote.repository.WorkExperienceRepository
import javax.inject.Inject


class GetAllWorkExperienceUseCase @Inject constructor(
    private val workExperienceRepository: WorkExperienceRepository
) {
    suspend fun execute(id: Long): List<WorkExperienceDto> {
      return workExperienceRepository.getAllWorkExperiences(id)
    }

}