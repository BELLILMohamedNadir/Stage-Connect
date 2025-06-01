package com.example.stageconnect.domain.usecases.project

import com.example.stageconnect.data.remote.repository.InternshipRepository
import com.example.stageconnect.data.remote.repository.ProjectRepository
import javax.inject.Inject


class DeleteProjectUseCase @Inject constructor(
    private val projectRepository: ProjectRepository
) {
    suspend fun execute(id: Long) {
        projectRepository.deleteProject(id)
    }

}