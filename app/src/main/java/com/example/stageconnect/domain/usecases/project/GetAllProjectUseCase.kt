package com.example.stageconnect.domain.usecases.project

import com.example.stageconnect.data.dtos.InternshipDto
import com.example.stageconnect.data.dtos.ProjectDto
import com.example.stageconnect.data.remote.repository.InternshipRepository
import com.example.stageconnect.data.remote.repository.ProjectRepository
import javax.inject.Inject


class GetAllProjectUseCase @Inject constructor(
    private val projectRepository: ProjectRepository
) {
    suspend fun execute(id: Long): List<ProjectDto> {
      return projectRepository.getAllProject(id)
    }

}