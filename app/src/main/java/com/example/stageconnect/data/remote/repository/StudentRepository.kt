package com.example.stageconnect.data.remote.repository

import com.example.stageconnect.data.dtos.RecruiterDto
import com.example.stageconnect.data.dtos.StudentDto
import com.example.stageconnect.data.remote.api.ApiService
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import javax.inject.Inject


interface StudentRepository {
    suspend fun updateStudent(studentDto: String, file: File): StudentDto
    suspend fun updateRecruiter(recruiterDto: String, file: File): RecruiterDto
    suspend fun addSkills(id:Long, skills: List<String>): List<String>
}

class StudentRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : StudentRepository {
    override suspend fun updateStudent(studentDto: String, file: File): StudentDto {
        val studentDtoRequestBody = studentDto.toRequestBody("text/plain".toMediaTypeOrNull())

        val fileUri = file.let {
            val requestBody = it.asRequestBody("image/*".toMediaTypeOrNull())
            MultipartBody.Part.createFormData("file", it.name, requestBody)
        }
        return apiService.updateStudent(studentDtoJson = studentDtoRequestBody, file = fileUri)
    }

    override suspend fun updateRecruiter(recruiterDto: String, file: File): RecruiterDto {
        val recruiterDtoRequestBody = recruiterDto.toRequestBody("text/plain".toMediaTypeOrNull())

        val fileUri = file.let {
            val requestBody = it.asRequestBody("image/*".toMediaTypeOrNull())
            MultipartBody.Part.createFormData("file", it.name, requestBody)
        }
        return apiService.updateRecruiter(recruiterDtoJson = recruiterDtoRequestBody, file = fileUri)
    }

    override suspend fun addSkills(id: Long, skills: List<String>): List<String> {
        return apiService.addSkills(id, skills)
    }

}
