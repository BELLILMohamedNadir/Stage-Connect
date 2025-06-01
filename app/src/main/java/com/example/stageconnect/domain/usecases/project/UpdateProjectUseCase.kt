package com.example.stageconnect.domain.usecases.project

import com.example.stageconnect.data.dtos.ProjectDto
import com.example.stageconnect.data.dtos.WorkExperienceDto
import com.example.stageconnect.data.remote.repository.ProjectRepository
import com.example.stageconnect.data.remote.repository.WorkExperienceRepository
import javax.inject.Inject


class UpdateProjectUseCase @Inject constructor(
    private val projectRepository: ProjectRepository
) {
    suspend fun execute(id: Long, projectDto: ProjectDto): ProjectDto {
      return projectRepository.updateProject(id, projectDto)
    }

}