package com.example.stageconnect.data.remote.repository

import com.example.stageconnect.data.remote.api.ApiService
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import javax.inject.Inject


// Repository Interface
interface RegisterRepository {
    suspend fun registerUser(authDtoJson: String, file: File): String
}

// Repository Implementation
class RegisterRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : RegisterRepository {

    override suspend fun registerUser(authDtoJson: String, file: File): String {
        val userDtoRequestBody = authDtoJson.toRequestBody("text/plain".toMediaTypeOrNull())

        val imagePart = file.let {
            val requestBody = it.asRequestBody("image/*".toMediaTypeOrNull())
            MultipartBody.Part.createFormData("file", it.name, requestBody)
        }

        return apiService.register(userDtoRequestBody, imagePart)
    }

}
