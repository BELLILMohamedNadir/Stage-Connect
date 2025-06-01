package com.example.stageconnect.domain.usecases.project

import com.example.stageconnect.data.dtos.ProjectDto
import com.example.stageconnect.data.remote.repository.ProjectRepository
import javax.inject.Inject


class CreateProjectUseCase @Inject constructor(
    private val projectRepository: ProjectRepository
) {
    suspend fun execute(projectDto: ProjectDto): ProjectDto {
        return projectRepository.createProject(projectDto)
    }
}
