package com.example.stageconnect.domain.usecases.internship

import com.example.stageconnect.data.remote.repository.InternshipRepository
import com.example.stageconnect.data.remote.repository.WorkExperienceRepository
import javax.inject.Inject


class DeleteInternshipUseCase @Inject constructor(
    private val internshipRepository: InternshipRepository
) {
    suspend fun execute(id: Long) {
        internshipRepository.deleteInternship(id)
    }

}