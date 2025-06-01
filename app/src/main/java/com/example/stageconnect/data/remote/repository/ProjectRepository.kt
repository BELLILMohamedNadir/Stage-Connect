package com.example.stageconnect.data.remote.repository

import com.example.stageconnect.data.dtos.CertificationDto
import com.example.stageconnect.data.dtos.EducationDto
import com.example.stageconnect.data.dtos.InternshipDto
import com.example.stageconnect.data.dtos.LanguageDto
import com.example.stageconnect.data.dtos.OfferDto
import com.example.stageconnect.data.dtos.ProjectDto
import com.example.stageconnect.data.remote.api.ApiService
import javax.inject.Inject


// Repository Interface
interface ProjectRepository {
    suspend fun createProject(projectDto: ProjectDto): ProjectDto
    suspend fun getAllProject(id: Long): List<ProjectDto>
    suspend fun updateProject(id: Long, projectDto: ProjectDto): ProjectDto
    suspend fun deleteProject(id: Long)
}

// Repository Implementation
class ProjectRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : ProjectRepository {
    override suspend fun createProject(projectDto: ProjectDto): ProjectDto {
        return apiService.createProject(projectDto)
    }

    override suspend fun getAllProject(id: Long): List<ProjectDto> {
        return apiService.getProjects(id)
    }

    override suspend fun updateProject(id: Long, projectDto: ProjectDto): ProjectDto {
        return apiService.updateProject(id, projectDto)
    }

    override suspend fun deleteProject(id: Long) {
        apiService.deleteProject(id)
    }

}
