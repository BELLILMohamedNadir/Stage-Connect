package com.example.stageconnect.domain.usecases.internship

import com.example.stageconnect.data.dtos.InternshipDto
import com.example.stageconnect.data.dtos.WorkExperienceDto
import com.example.stageconnect.data.remote.repository.InternshipRepository
import com.example.stageconnect.data.remote.repository.WorkExperienceRepository
import javax.inject.Inject


class GetAllInternshipUseCase @Inject constructor(
    private val internshipRepository: InternshipRepository
) {
    suspend fun execute(id: Long): List<InternshipDto> {
      return internshipRepository.getAllInternship(id)
    }

}