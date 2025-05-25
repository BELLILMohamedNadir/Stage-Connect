package com.example.stageconnect.domain.usecases.create

import com.example.stageconnect.data.dtos.ApplicationDto
import com.example.stageconnect.data.dtos.EducationDto
import com.example.stageconnect.data.dtos.InternshipDto
import com.example.stageconnect.data.dtos.LanguageDto
import com.example.stageconnect.data.dtos.OfferDto
import com.example.stageconnect.data.dtos.ProjectDto
import com.example.stageconnect.data.remote.repository.ApplicationRepository
import com.example.stageconnect.data.remote.repository.EducationRepository
import com.example.stageconnect.data.remote.repository.InternshipRepository
import com.example.stageconnect.data.remote.repository.LanguageRepository
import com.example.stageconnect.data.remote.repository.OfferRepository
import com.example.stageconnect.data.remote.repository.ProjectRepository
import javax.inject.Inject


class CreateProjectUseCase @Inject constructor(
    private val projectRepository: ProjectRepository
) {
    suspend fun execute(projectDto: ProjectDto): ProjectDto {
        return projectRepository.createProject(projectDto)
    }
}
