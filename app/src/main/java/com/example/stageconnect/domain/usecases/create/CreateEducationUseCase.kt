package com.example.stageconnect.domain.usecases.create

import com.example.stageconnect.data.dtos.ApplicationDto
import com.example.stageconnect.data.dtos.EducationDto
import com.example.stageconnect.data.remote.repository.ApplicationRepository
import com.example.stageconnect.data.remote.repository.EducationRepository
import javax.inject.Inject


class CreateEducationUseCase @Inject constructor(
    private val educationRepository: EducationRepository
) {
    suspend fun execute(educationDto: EducationDto): EducationDto {
        return educationRepository.createEducation(educationDto)
    }
}
