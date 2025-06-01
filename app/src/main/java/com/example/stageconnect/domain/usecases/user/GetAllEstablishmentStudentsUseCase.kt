package com.example.stageconnect.domain.usecases.user

import com.example.stageconnect.data.dtos.StudentDto
import com.example.stageconnect.data.remote.repository.EstablishmentRepository
import javax.inject.Inject


class GetAllEstablishmentStudentsUseCase @Inject constructor(
    private val establishmentRepository: EstablishmentRepository
) {

    suspend fun execute(id:Long): List<StudentDto> {
        return establishmentRepository.getAllEstablishmentStudents(id)
    }

}
