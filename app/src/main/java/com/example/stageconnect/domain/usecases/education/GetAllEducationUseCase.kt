package com.example.stageconnect.domain.usecases.education

import com.example.stageconnect.data.dtos.EducationDto
import com.example.stageconnect.data.dtos.WorkExperienceDto
import com.example.stageconnect.data.remote.repository.EducationRepository
import com.example.stageconnect.data.remote.repository.WorkExperienceRepository
import javax.inject.Inject


class GetAllEducationUseCase @Inject constructor(
    private val educationRepository: EducationRepository
) {
    suspend fun execute(id: Long): List<EducationDto> {
      return educationRepository.getAllEducation(id)
    }

}