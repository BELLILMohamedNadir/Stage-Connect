package com.example.stageconnect.domain.usecases.create

import com.example.stageconnect.data.dtos.ApplicationDto
import com.example.stageconnect.data.dtos.EducationDto
import com.example.stageconnect.data.dtos.InternshipDto
import com.example.stageconnect.data.remote.repository.ApplicationRepository
import com.example.stageconnect.data.remote.repository.EducationRepository
import com.example.stageconnect.data.remote.repository.InternshipRepository
import javax.inject.Inject


class CreateInternshipUseCase @Inject constructor(
    private val internshipRepository: InternshipRepository
) {
    suspend fun execute(internshipDto: InternshipDto): InternshipDto {
        return internshipRepository.createInternship(internshipDto)
    }
}
