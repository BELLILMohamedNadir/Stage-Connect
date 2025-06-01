package com.example.stageconnect.domain.usecases.user

import com.example.stageconnect.data.remote.repository.StudentRepository
import javax.inject.Inject


class AddSkillsUseCase @Inject constructor(
    private val studentRepository: StudentRepository
) {
    suspend fun execute(id: Long, skills: List<String>): List<String> {
      return studentRepository.addSkills(id, skills)
    }

}