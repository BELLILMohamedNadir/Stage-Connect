package com.example.stageconnect.data.remote.repository

import com.example.stageconnect.data.remote.api.ApiService
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.ResponseBody
import retrofit2.Response
import java.io.File
import javax.inject.Inject


// Repository Interface
interface FileRepository {
    suspend fun uploadFile(userId: Long, file: File, uploadPhoto: Boolean): String
    suspend fun downloadFile(fileName: String): Response<ResponseBody>
}

// Repository Implementation
class FileRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : FileRepository {
    override suspend fun uploadFile(userId: Long, file: File, uploadPhoto: Boolean): String {

        val mediaType = if (file.name.endsWith(".pdf", ignoreCase = true)) {
            "application/pdf"
        } else {
            "image/*"
        }

        val pdfPart = file.let {
            val requestBody = it.asRequestBody(mediaType.toMediaTypeOrNull())
            MultipartBody.Part.createFormData("file", it.name, requestBody)
        }

        return if (uploadPhoto)
            apiService.uploadPhoto(userId, pdfPart)
        else
            apiService.uploadFile(userId, pdfPart)
    }

    override suspend fun downloadFile(fileName: String): Response<ResponseBody> {
        return apiService.downloadFile(fileName)
    }


}
